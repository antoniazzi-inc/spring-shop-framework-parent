package com.github.antoniazzi.inc.backend.commons.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;

import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ProductType;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.VoucherSupport;
import com.github.antoniazzi.inc.backend.commons.model.dto.IdDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.CartOrderDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderDiscountLineDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderLineDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderLinePaymentScheduleDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderProductAttributeValueDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.IdQty;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.PromotionItemsWrapper;
import com.github.antoniazzi.inc.backend.commons.util.H;

public class PricingEngine {

	private static final BigDecimal PERCENTAGE = BigDecimal.valueOf(100);

	private final ZonedDateTime now = ZonedDateTime.now();

	private final CartOrderDto order;

	private final CurrencyUnit currency;

	private final CurrencyUnit currencyMoney = Monetary.getCurrency("MONEY");
	private final CurrencyUnit currencyPoints = Monetary.getCurrency("POINTS");
	private final CurrencyUnit currencyMinutes = Monetary.getCurrency("MINUTES");

	private Money totalOrderLinesNettoAmount;
	private Money totalOrderLinesGrossAmount;
	private Money totalOrderDiscountsAmount;
	private Money administrativeCosts;
	private Money totalOrderTaxAmount;
	private Money totalOrderAmount;

	private Money totalOrderLinesVoucherMoneyAmount = Money.of(0, currencyMoney);
	private Money totalOrderLinesVoucherPointsAmount = Money.of(0, currencyPoints);;
	private Money totalOrderLinesVoucherMinutesAmount = Money.of(0, currencyMinutes);;

	private boolean noShippingCost = false;

	private Integer totalProducts = 0;

	private Map<BigDecimal, Money> totalTaxesMap = new HashMap<>();

	private Map<Long, Long> freeItemsProductsMap = new HashMap<>();
	private Map<Long, Long> freeItemsAttributeValuesMap = new HashMap<>();

	public PricingEngine(CartOrderDto order) {
		this.order = order;

		// if currency is null set it to EUR
		if (order.getCurrency() != null && !order.getCurrency().isEmpty()) {
			this.currency = Monetary.getCurrency(order.getCurrency().toUpperCase());
		} else {
			this.currency = Monetary.getCurrency("EUR");
		}

		// initialize default values with currency
		totalOrderLinesNettoAmount = Money.of(0, currency);
		totalOrderLinesGrossAmount = Money.of(0, currency);
		totalOrderDiscountsAmount = Money.of(0, currency);
		administrativeCosts = Money.of(0, currency);
		totalOrderTaxAmount = Money.of(0, currency);
		totalOrderAmount = Money.of(0, currency);

		calculate();
	}

	private void calculate() {
		// check if order lines exist
		if (!nullOrEmpty(order.getOrderLines())) {
			validateOrderLines(order.getOrderLines());
			prepareOrderLines();

			// check if order discount lines exist
			if (!nullOrEmpty(order.getOrderDiscountLines())) {
				validateOrderDiscountLines(order.getOrderDiscountLines());
				prepareOrderDiscountLines();
				calculateOrderDiscountLines();

				// remove temporary ids
				for (OrderDiscountLineDto odl : order.getOrderDiscountLines()) {
					odl.setId(null);
				}
			}

			calculateOrderLines();

			// remove temporary ids
			for (OrderLineDto ol : order.getOrderLines()) {
				ol.setId(null);
			}
		}

		totalOrderAmount = totalOrderLinesGrossAmount;

		calculateOrderPaymentMethodCosts();

		order.setNettoAmount(totalOrderLinesNettoAmount.getNumberStripped());
		order.setDiscountAmount(totalOrderDiscountsAmount.getNumberStripped());
		order.setTaxAmount(totalOrderTaxAmount.getNumberStripped());
		order.setPaymentMethodCostAmount(administrativeCosts.getNumberStripped());
		order.setTotalAmount(totalOrderAmount.getNumberStripped());
	}

	/**
	 * Checks if the Order Lines contain all data needed by the pricing engine
	 * 
	 * @param orderLines
	 */
	private void validateOrderLines(List<OrderLineDto> orderLines) {
		for (int i = 0; i < orderLines.size(); i++) {
			OrderLineDto ol = orderLines.get(i);

			if (ol.getQuantity() == null || ol.getQuantity() < 0) {
				throw new BadRequestException("Invalid quantity in OrderLine with index " + i);
			}

			if (ol.getOrderProduct() == null) {
				throw new BadRequestException("No Product in OrderLine with index " + i);
			}

			if (ol.getOrderProduct().getProductId() == null) {
				throw new BadRequestException("No productId provided for OrderLine with index " + i);
			}

			if (ol.getOrderProduct().getProductPrice() == null || ol.getOrderProduct().getProductPrice().compareTo(new BigDecimal(0)) == -1) {
				throw new BadRequestException("Invalid Product Price for productId " + ol.getOrderProduct().getProductId() + " in OrderLine with index " + i);
			}

			if (ol.getOrderProduct().getTaxPercentage() == null || ol.getOrderProduct().getTaxPercentage().compareTo(new BigDecimal(0)) == -1) {
				throw new BadRequestException("Invalid Tax Percentage for productId " + ol.getOrderProduct().getProductId() + " in OrderLine with index " + i);
			}

			if (ol.getOrderProduct().getProductType() == null) {
				throw new BadRequestException(
						"No Product Type provided for productId " + ol.getOrderProduct().getProductId() + " in OrderLine with index " + i);
			}

			if (ol.getPayWithVoucher() != null && ol.getPayWithVoucher()) {
				if (ol.getOrderProduct().getVoucherSupport() == null || ol.getOrderProduct().getVoucherSupport().equals(VoucherSupport.NONE)) {
					throw new BadRequestException(
							"Voucher not supported for Product with id " + ol.getOrderProduct().getProductId() + " in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getPaymentScheduleId() != null) {
					throw new BadRequestException("Payment Schedules not supported with Voucher Payments in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getVoucherValue() == null || ol.getOrderProduct().getVoucherValue().compareTo(new BigDecimal(0)) == -1) {
					throw new BadRequestException(
							"Invalid voucherValue for Product with id " + ol.getOrderProduct().getProductId() + " in OrderLine with index " + i);
				}
			}

			if (ol.getOrderProduct().getPaymentScheduleId() != null) {
				if (ol.getPayWithVoucher() != null && ol.getPayWithVoucher()) {
					throw new BadRequestException("Payment Schedules not supported with Voucher Payments in OrderLine with index " + i);
				}

				if (nullOrEmpty(ol.getOrderLinePaymentSchedules())) {
					throw new BadRequestException("Payment Schedules not provided in OrderLine with index " + i);
				}
			}

			if (ol.getOrderProduct().getProductType().equals(ProductType.VOUCHER)) {
				if (ol.getOrderProduct().getOrderProductPurchasedVoucher() == null) {
					throw new BadRequestException("No Product Voucher provided in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getOrderProductPurchasedVoucher().getVoucherType() == null
						|| ol.getOrderProduct().getOrderProductPurchasedVoucher().getTypeVoucherId() == null) {
					throw new BadRequestException("Product Voucher DTO not valid in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getOrderProductPurchasedVoucher().getValue() == null
						|| ol.getOrderProduct().getOrderProductPurchasedVoucher().getValue().compareTo(new BigDecimal(0)) == -1) {
					throw new BadRequestException("Invalid Product Voucher value in OrderLine with index " + i);
				}
			}

			if (ol.getOrderProduct().getProductType().equals(ProductType.COURSE)) {
				if (ol.getOrderProduct().getOrderProductEvent() == null) {
					throw new BadRequestException("No Product Event provided in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getOrderProductEvent().getEventId() == null || ol.getOrderProduct().getOrderProductEvent().getCourseId() == null
						|| ol.getOrderProduct().getOrderProductEvent().getEventStart() == null) {
					throw new BadRequestException("Product Event DTO not valid in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getOrderProductEvent().getEventStart().isBefore(now)) {
					throw new BadRequestException("Product Event time has pased in OrderLine with index " + i);
				}

				if (ol.getOrderProduct().getOrderProductEvent().getPrice() != null
						&& ol.getOrderProduct().getOrderProductEvent().getPrice().compareTo(new BigDecimal(0)) == -1) {
					throw new BadRequestException("Invalid Product Event price in OrderLine with index " + i);
				}
			}

			if (ol.getOrderProduct().getProductType().equals(ProductType.PHYSICAL) && ol.getOrderLineDeliveryMethod() == null) {
				throw new BadRequestException("No Delivery Method provided in OrderLine with index " + i);
			}

			if (!nullOrEmpty(ol.getOrderProduct().getOrderProductAttributeValues())) {
				for (OrderProductAttributeValueDto opav : ol.getOrderProduct().getOrderProductAttributeValues()) {
					if (opav.getAttributeId() == null) {
						throw new BadRequestException("No attributeId provided in OrderLine with index " + i);
					}

					if (opav.getAttributeValueId() == null) {
						throw new BadRequestException("No attributeValueId provided in OrderLine with index " + i);
					}

					if (opav.getAttributeValuePrice() == null || opav.getAttributeValuePrice().compareTo(new BigDecimal(0)) == -1) {
						throw new BadRequestException("Invalid Attribute Value Price in OrderLine with index " + i);
					}
				}
			}
		}
	}

	/**
	 * Checks if the Order Discount Lines contain all data needed by the pricing engine
	 * 
	 * @param orderLines
	 */
	public void validateOrderDiscountLines(List<OrderDiscountLineDto> orderDiscountLines) {
		for (int i = 0; i < orderDiscountLines.size(); i++) {
			OrderDiscountLineDto odl = orderDiscountLines.get(i);

			if (odl.getDiscountId() == null) {
				throw new BadRequestException("No discountId provided in OrderDiscountLine with index " + i);
			}

			if (odl.getOrderPromotion() == null) {
				throw new BadRequestException("No Promotion provided in OrderDiscountLine with index " + i);
			}

			if (odl.getOrderPromotion().getPromotionId() == null) {
				throw new BadRequestException("No promotionId provided in OrderDiscountLine with index " + i);
			}

			if (odl.getOrderPromotion().getPromotionType() == null) {
				throw new BadRequestException("No Promotion Type provided in OrderDiscountLine with index " + i);
			}

			if (odl.getOrderPromotion().getAvailableFrom() == null) {
				throw new BadRequestException("No availableFrom provided in OrderDiscountLine with index " + i);
			}

			if (!(now.isAfter(odl.getOrderPromotion().getAvailableFrom())
					&& (odl.getOrderPromotion().getAvailableTo() == null || now.isBefore(odl.getOrderPromotion().getAvailableTo())))) {
				throw new BadRequestException("Promotion not in time frame in OrderDiscountLine with index " + i);
			}

			if ((odl.getEntireOrder() == null || odl.getEntireOrder() == false) && (nullOrEmpty(odl.getOrderPromotion().getProducts())
					&& nullOrEmpty(odl.getOrderPromotion().getAttributeValues()) && nullOrEmpty(odl.getOrderPromotion().getPromotionProductCategories()))) {
				throw new BadRequestException("Promotion not valid in OrderDiscountLine with index " + i);
			}

			if (odl.getPercentage() == null && odl.getFixed() == null && odl.getNoShipping() == null && odl.getFreeItemsJson() == null) {
				throw new BadRequestException("Invalid Discount in OrderDiscountLine with index " + i);
			}

			if (odl.getPercentage() != null) {
				if (odl.getFixed() != null || (odl.getNoShipping() != null && odl.getNoShipping()) || odl.getFreeItemsJson() != null) {
					throw new BadRequestException("Multiple values in Discount in OrderDiscountLine with index " + i);
				}

				if (odl.getPercentage().compareTo(new BigDecimal(0)) == -1) {
					throw new BadRequestException("Invalid Percentage value in Discount in OrderDiscountLine with index " + i);
				}
			}

			if (odl.getFixed() != null) {
				if (odl.getPercentage() != null || (odl.getNoShipping() != null && odl.getNoShipping()) || odl.getFreeItemsJson() != null) {
					throw new BadRequestException("Multiple values in Discount in OrderDiscountLine with index " + i);
				}

				if (odl.getFixed().compareTo(new BigDecimal(0)) == -1) {
					throw new BadRequestException("Invalid Fixed value in Discount in OrderDiscountLine with index " + i);
				}
			}

			if (odl.getNoShipping() != null && odl.getNoShipping()) {
				if (odl.getFixed() != null || odl.getPercentage() != null || odl.getFreeItemsJson() != null) {
					throw new BadRequestException("Multiple values in Discount in OrderDiscountLine with index " + i);
				}
			}

			if (odl.getFreeItemsJson() != null) {
				if (odl.getFixed() != null || (odl.getNoShipping() != null && odl.getNoShipping()) || odl.getPercentage() != null) {
					throw new BadRequestException("Multiple values in Discount in OrderDiscountLine with index " + i);
				}

				if (nullOrEmpty(odl.getFreeItemsJson().getProducts()) && nullOrEmpty(odl.getFreeItemsJson().getAttributeValues())) {
					throw new BadRequestException("Invalid Free Items in Discount in OrderDiscountLine with index " + i);
				}

				if (!nullOrEmpty(odl.getFreeItemsJson().getProducts())) {
					for (IdQty idQty : odl.getFreeItemsJson().getProducts()) {
						if (idQty.getId() == null || idQty.getId() < 0) {
							throw new BadRequestException("Invalid Id in Free Items in OrderDiscountLine with index " + i);
						}

						if (idQty.getQuantity() == null || idQty.getQuantity() < 0) {
							throw new BadRequestException("Invalid Quantity in Free Items in OrderDiscountLine with index " + i);
						}
					}
				}

				if (!nullOrEmpty(odl.getFreeItemsJson().getAttributeValues())) {
					for (IdQty idQty : odl.getFreeItemsJson().getAttributeValues()) {
						if (idQty.getId() == null || idQty.getId() < 0) {
							throw new BadRequestException("Invalid Id in Free Items in OrderDiscountLine with index " + i);
						}

						if (idQty.getQuantity() == null || idQty.getQuantity() < 0) {
							throw new BadRequestException("Invalid Quantity in Free Items in OrderDiscountLine with index " + i);
						}
					}
				}
			}
		}
	}

	/**
	 * Prepares DTO and calculates and sets netto prices
	 */
	private void prepareOrderLines() {
		// temporary index to be assigned as id
		long i = 0l;

		for (OrderLineDto ol : order.getOrderLines()) {
			boolean payWithVoucher = false;

			if (ol.getPayWithVoucher() != null && ol.getPayWithVoucher()) {
				payWithVoucher = true;
			}

			// set temporary id to each order line
			ol.setId(i);

			// add to the total products
			totalProducts += ol.getQuantity();

			BigDecimal totalOrderLineNettoAmount = new BigDecimal(0);
			BigDecimal totalOrderLineVoucherAmount = new BigDecimal(0);

			if (!payWithVoucher) {
				if (ol.getOrderProduct().getPaymentScheduleId() == null) {
					// product price multiplied by the quantity
					totalOrderLineNettoAmount = ol.getOrderProduct().getProductPrice().multiply(new BigDecimal(ol.getQuantity()));
				} else {
					for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
						olps.setQuantity(ol.getQuantity());
						olps.setDiscountAmount(new BigDecimal(0));
						olps.setTaxPercentage(ol.getOrderProduct().getTaxPercentage());
						olps.setNettoAmount(olps.getNettoAmount().multiply(new BigDecimal(ol.getQuantity())));
					}

					totalOrderLineNettoAmount = ol.getOrderLinePaymentSchedules().remove(0).getNettoAmount();
				}
			} else {
				totalOrderLineVoucherAmount = ol.getOrderProduct().getVoucherValue().multiply(new BigDecimal(ol.getQuantity()));
			}

			// if attribute values available, to the totalOrderLineNettoAmount
			if (!nullOrEmpty(ol.getOrderProduct().getOrderProductAttributeValues())) {
				for (OrderProductAttributeValueDto opav : ol.getOrderProduct().getOrderProductAttributeValues()) {
					if (!payWithVoucher) {
						if (opav.getAttributeValuePrice() != null) {
							// attribute value price multiplied by the quantity
							BigDecimal totalOrderLineAttributeValuesAmount = opav.getAttributeValuePrice().multiply(BigDecimal.valueOf(ol.getQuantity()));

							totalOrderLineNettoAmount = totalOrderLineNettoAmount.add(totalOrderLineAttributeValuesAmount);
						}
					} else {
						if (opav.getAttributeValueVoucherValue() != null) {
							BigDecimal totalOrderLineAttributeValuesVoucherAmount = opav.getAttributeValueVoucherValue()
									.multiply(BigDecimal.valueOf(ol.getQuantity()));

							totalOrderLineVoucherAmount = totalOrderLineVoucherAmount.add(totalOrderLineAttributeValuesVoucherAmount);
						}
					}
				}
			}

			if (ol.getOrderProduct().getProductType().equals(ProductType.COURSE)) {
				if (!payWithVoucher) {
					if (ol.getOrderProduct().getOrderProductEvent().getPrice() != null) {
						// event additional price multiplied by the quantity
						BigDecimal additionalEventPriceAmount = ol.getOrderProduct().getOrderProductEvent().getPrice()
								.multiply(BigDecimal.valueOf(ol.getQuantity()));

						totalOrderLineNettoAmount = totalOrderLineNettoAmount.add(additionalEventPriceAmount);
					}
				} else {
					if (ol.getOrderProduct().getOrderProductEvent().getVoucherValue() != null) {
						BigDecimal totalOrderLineAttributeValuesVoucherAmount = ol.getOrderProduct().getOrderProductEvent().getVoucherValue()
								.multiply(BigDecimal.valueOf(ol.getQuantity()));

						totalOrderLineVoucherAmount = totalOrderLineVoucherAmount.add(totalOrderLineAttributeValuesVoucherAmount);
					}
				}
			}

			// round and set the price on the DTO
			totalOrderLineNettoAmount = totalOrderLineNettoAmount.setScale(2, RoundingMode.HALF_EVEN);
			ol.setNettoAmount(totalOrderLineNettoAmount);

			// set default 0 discount amount
			ol.setDiscountAmount(new BigDecimal(0));

			// TODO apply from delivery method costs per region...
			// TODO vouchers?
			if (ol.getOrderProduct().getProductType().equals(ProductType.PHYSICAL)) {
				// default 0 for now
				ol.setShippingCostAmount(new BigDecimal(0));
			}

			// check for voucher payment
			if (!payWithVoucher) {
				// add to the pricing engine order lines total
				Money totalOrderLineNettoAmountMoney = Money.of(ol.getNettoAmount(), currency);

				totalOrderLinesNettoAmount = totalOrderLinesNettoAmount.add(totalOrderLineNettoAmountMoney);
			} else {
				if (ol.getOrderProduct().getVoucherSupport().equals(VoucherSupport.MONEY)) {
					totalOrderLinesVoucherMoneyAmount = totalOrderLinesVoucherMoneyAmount.add(Money.of(totalOrderLineVoucherAmount, currencyMoney));
				} else if (ol.getOrderProduct().getVoucherSupport().equals(VoucherSupport.TIME)) {
					totalOrderLinesVoucherMinutesAmount = totalOrderLinesVoucherMinutesAmount.add(Money.of(totalOrderLineVoucherAmount, currencyMinutes));
				} else if (ol.getOrderProduct().getVoucherSupport().equals(VoucherSupport.POINTS)) {
					totalOrderLinesVoucherPointsAmount = totalOrderLinesVoucherPointsAmount.add(Money.of(totalOrderLineVoucherAmount, currencyPoints));
				}
			}

			i++;
		}
	}

	/**
	 * Prepares and sorts DTO and calculates and sets netto prices
	 */
	private void prepareOrderDiscountLines() {
		// temporary index to be assigned as id
		long i = 0l;

		for (OrderDiscountLineDto odl : order.getOrderDiscountLines()) {
			// set temporary id to each order line
			odl.setId(i);
		}

		// sort discounts so they can be applied in order
		Collections.sort(order.getOrderDiscountLines(),
				Comparator.comparing(OrderDiscountLineDto::getPercentage, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(OrderDiscountLineDto::getFixed, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparing(OrderDiscountLineDto::getNoShipping, Comparator.nullsLast(Comparator.naturalOrder())));
	}

	private void calculateOrderDiscountLines() {
		for (OrderDiscountLineDto odl : order.getOrderDiscountLines()) {
			if (odl.getFreeItemsJson() != null) {
				if (!H.NullOrEmpty(odl.getFreeItemsJson().getProducts())) {
					for (IdQty idQty : odl.getFreeItemsJson().getProducts()) {
						if (!freeItemsProductsMap.containsKey(idQty.getId())) {
							freeItemsProductsMap.put(idQty.getId(), idQty.getQuantity());
						} else {
							freeItemsProductsMap.put(idQty.getId(), freeItemsProductsMap.get(idQty.getId()) + idQty.getQuantity());
						}
					}
				}

				if (!H.NullOrEmpty(odl.getFreeItemsJson().getAttributeValues())) {
					for (IdQty idQty : odl.getFreeItemsJson().getAttributeValues()) {
						if (!freeItemsAttributeValuesMap.containsKey(idQty.getId())) {
							freeItemsAttributeValuesMap.put(idQty.getId(), idQty.getQuantity());
						} else {
							freeItemsAttributeValuesMap.put(idQty.getId(), freeItemsAttributeValuesMap.get(idQty.getId()) + idQty.getQuantity());
						}
					}
				}
			}
		}

		if (freeItemsProductsMap != null && !freeItemsProductsMap.isEmpty()) {
			for (Map.Entry<Long, Long> product : freeItemsProductsMap.entrySet()) {
				if (product.getValue() > 0) {
					for (OrderLineDto ol : order.getOrderLines()) {
						if (product.getKey() == ol.getOrderProduct().getProductId()) {
							final long quantity;

							if (ol.getQuantity() > product.getValue()) {
								quantity = product.getValue();

								if (ol.getOrderProduct().getPaymentScheduleId() != null) {
									for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
										olps.setQuantity(Long.valueOf(quantity).intValue());
										olps.setNettoAmount(olps.getNettoAmount().divide(new BigDecimal(ol.getQuantity())).multiply(new BigDecimal(quantity)));
									}
								}
							} else {
								quantity = ol.getQuantity();

								if (ol.getOrderProduct().getPaymentScheduleId() != null) {
									ol.getOrderProduct().setPaymentScheduleId(null);
									ol.setOrderLinePaymentSchedules(new ArrayList<>());
								}
							}

							BigDecimal subtrahend = ol.getOrderProduct().getProductPrice().multiply(new BigDecimal(quantity));

							product.setValue(product.getValue() - quantity);

							ol.setNettoAmount(ol.getNettoAmount().subtract(subtrahend));

							totalOrderLinesNettoAmount = totalOrderLinesNettoAmount.subtract(Money.of(subtrahend, currency));
						}
					}
				}
			}
		}

		if (freeItemsAttributeValuesMap != null && !freeItemsAttributeValuesMap.isEmpty()) {
			for (Map.Entry<Long, Long> attributeValue : freeItemsAttributeValuesMap.entrySet()) {
				if (attributeValue.getValue() > 0) {
					for (OrderLineDto ol : order.getOrderLines()) {
						if (!nullOrEmpty(ol.getOrderProduct().getOrderProductAttributeValues())) {
							for (OrderProductAttributeValueDto opav : ol.getOrderProduct().getOrderProductAttributeValues()) {
								if (attributeValue.getKey() == opav.getAttributeValueId()) {
									final long quantity;

									if (ol.getQuantity() > attributeValue.getValue()) {
										quantity = attributeValue.getValue();
									} else {
										quantity = ol.getQuantity();
									}

									BigDecimal subtrahend = opav.getAttributeValuePrice().multiply(new BigDecimal(quantity));

									attributeValue.setValue(attributeValue.getValue() - quantity);

									ol.setNettoAmount(ol.getNettoAmount().subtract(subtrahend));

									totalOrderLinesNettoAmount = totalOrderLinesNettoAmount.subtract(Money.of(subtrahend, currency));
								}
							}
						}
					}
				}
			}
		}

		for (OrderDiscountLineDto odl : order.getOrderDiscountLines()) {
			if (odl.getEntireOrder() != null && odl.getEntireOrder() == true) {
				if (odl.getPercentage() != null) {
					for (OrderLineDto ol : order.getOrderLines()) {
						if (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher()) {
							// percentage of the order line netto
							BigDecimal discountAmount = ol.getNettoAmount().multiply(odl.getPercentage()).divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN);

							ol.setDiscountAmount(ol.getDiscountAmount().add(discountAmount));

							// apply discount for each payment schedule
							if (ol.getOrderProduct().getPaymentScheduleId() != null) {
								for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
									BigDecimal olpsDiscountAmount = olps.getNettoAmount().multiply(odl.getPercentage()).divide(PERCENTAGE, 2,
											RoundingMode.HALF_EVEN);

									olps.setDiscountAmount(olps.getDiscountAmount().add(olpsDiscountAmount));
								}
							}
						}
					}
				} else if (odl.getFixed() != null) {
					BigDecimal totalPaymentSchedulesNettoAmount = new BigDecimal(0);

					for (OrderLineDto ol : order.getOrderLines()) {
						if (ol.getOrderProduct().getPaymentScheduleId() != null) {
							for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
								totalPaymentSchedulesNettoAmount.add(olps.getNettoAmount());
							}
						}
					}

					BigDecimal totalAmountToBeCalculated = totalOrderLinesNettoAmount.getNumberStripped().add(totalPaymentSchedulesNettoAmount);

					for (OrderLineDto ol : order.getOrderLines()) {
						if (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher()) {
							// apply fixed discount appropriately based on percentage of total
							BigDecimal discountAmount = ol.getNettoAmount().multiply(odl.getFixed()).divide(totalAmountToBeCalculated, 2,
									RoundingMode.HALF_EVEN);

							ol.setDiscountAmount(ol.getDiscountAmount().add(discountAmount));

							if (ol.getOrderProduct().getPaymentScheduleId() != null) {
								for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
									BigDecimal olpsDiscountAmount = olps.getNettoAmount().multiply(odl.getFixed()).divide(totalAmountToBeCalculated, 2,
											RoundingMode.HALF_EVEN);

									olps.setDiscountAmount(olps.getDiscountAmount().add(olpsDiscountAmount));
								}
							}
						}
					}
				} else if (odl.getNoShipping() != null && odl.getNoShipping()) {
					noShippingCost = true;
					// set shipping cost to 0 for all physical type products
					for (OrderLineDto ol : order.getOrderLines()) {
						if (ol.getOrderProduct().getProductType().equals(ProductType.PHYSICAL)) {
							ol.setShippingCostAmount(new BigDecimal(0));
						}
					}
				}
			} else {
				Set<OrderLineDto> applicableOrderLines = new HashSet<>();

				// add applicable order lines to set that match the product id
				if (!nullOrEmpty(odl.getOrderPromotion().getProducts())) {
					for (OrderLineDto ol : order.getOrderLines()) {
						for (IdDto product : odl.getOrderPromotion().getProducts()) {
							if (ol.getOrderProduct().getProductId().equals(product.getId())) {
								if (odl.getPercentage() != null && (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher())) {
									BigDecimal discountAmount = ol.getOrderProduct().getProductPrice().multiply(new BigDecimal(ol.getQuantity()))
											.multiply(odl.getPercentage()).divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN);

									ol.setDiscountAmount(ol.getDiscountAmount().add(discountAmount));

									// apply discount for each payment schedule
									if (ol.getOrderProduct().getPaymentScheduleId() != null) {
										for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
											BigDecimal olpsDiscountAmount = olps.getNettoAmount().multiply(odl.getPercentage()).divide(PERCENTAGE, 2,
													RoundingMode.HALF_EVEN);

											olps.setDiscountAmount(olps.getDiscountAmount().add(olpsDiscountAmount));
										}
									}
								}

								applicableOrderLines.add(ol);
							}
						}
					}
				}

				// add applicable order lines to set that match the attribute value id
				if (!nullOrEmpty(odl.getOrderPromotion().getAttributeValues())) {
					for (OrderLineDto ol : order.getOrderLines()) {
						if (!nullOrEmpty(ol.getOrderProduct().getOrderProductAttributeValues())) {
							for (IdDto attributeValue : odl.getOrderPromotion().getAttributeValues()) {
								for (OrderProductAttributeValueDto orderProductAttributeValue : ol.getOrderProduct().getOrderProductAttributeValues()) {
									if (orderProductAttributeValue.getAttributeValueId().equals(attributeValue.getId())) {
										if (odl.getPercentage() != null && (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher())) {
											BigDecimal discountAmount = orderProductAttributeValue.getAttributeValuePrice()
													.multiply(new BigDecimal(ol.getQuantity())).multiply(odl.getPercentage())
													.divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN);

											ol.setDiscountAmount(ol.getDiscountAmount().add(discountAmount));
										}

										applicableOrderLines.add(ol);
									}
								}
							}
						}
					}
				}

				// add applicable order lines to set that match the category id
				if (!nullOrEmpty(odl.getOrderPromotion().getPromotionProductCategories())) {
					for (OrderLineDto ol : order.getOrderLines()) {
						if (!nullOrEmpty(ol.getOrderProduct().getProductCategories())) {
							for (IdDto promotionProductCategory : odl.getOrderPromotion().getPromotionProductCategories()) {
								for (IdDto productCategory : ol.getOrderProduct().getProductCategories()) {
									if (promotionProductCategory.getId() == productCategory.getId()) {
										if (applicableOrderLines.add(ol)) {
											if (odl.getPercentage() != null && (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher())) {
												BigDecimal discountAmount = ol.getOrderProduct().getProductPrice().multiply(new BigDecimal(ol.getQuantity()))
														.multiply(odl.getPercentage()).divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN);

												ol.setDiscountAmount(ol.getDiscountAmount().add(discountAmount));

												// apply discount for each payment schedule
												if (ol.getOrderProduct().getPaymentScheduleId() != null) {
													for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
														BigDecimal olpsDiscountAmount = olps.getNettoAmount().multiply(odl.getPercentage()).divide(PERCENTAGE,
																2, RoundingMode.HALF_EVEN);

														olps.setDiscountAmount(olps.getDiscountAmount().add(olpsDiscountAmount));
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

				if (!nullOrEmpty(applicableOrderLines)) {
					if (odl.getFixed() != null) {
						BigDecimal totalApplicableOrderLinesNetto = new BigDecimal(0);

						for (OrderLineDto ol : applicableOrderLines) {
							if (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher()) {
								// calculate total netto of applicable order lines
								totalApplicableOrderLinesNetto = totalApplicableOrderLinesNetto.add(ol.getNettoAmount());
							}
						}

						BigDecimal totalPaymentSchedulesNettoAmount = new BigDecimal(0);

						for (OrderLineDto ol : applicableOrderLines) {
							if (ol.getOrderProduct().getPaymentScheduleId() != null) {
								for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
									totalPaymentSchedulesNettoAmount.add(olps.getNettoAmount());
								}
							}
						}

						BigDecimal totalAmountToBeCalculated = totalOrderLinesNettoAmount.getNumberStripped().add(totalPaymentSchedulesNettoAmount);

						for (OrderLineDto ol : applicableOrderLines) {
							if (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher()) {
								// apply fixed discount appropriately based on percentage of total
								BigDecimal discountAmount = ol.getNettoAmount().multiply(odl.getFixed()).divide(totalAmountToBeCalculated, 2,
										RoundingMode.HALF_EVEN);

								ol.setDiscountAmount(ol.getDiscountAmount().add(discountAmount));

								if (ol.getOrderProduct().getPaymentScheduleId() != null) {
									for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
										BigDecimal olpsDiscountAmount = olps.getNettoAmount().multiply(odl.getFixed()).divide(totalAmountToBeCalculated, 2,
												RoundingMode.HALF_EVEN);

										olps.setDiscountAmount(olps.getDiscountAmount().add(olpsDiscountAmount));
									}
								}
							}
						}
					} else if (odl.getNoShipping()) {
						for (OrderLineDto ol : applicableOrderLines) {
							// set shipping cost to 0 for all applicable physical products
							if (ol.getOrderProduct().getProductType().equals(ProductType.PHYSICAL)) {
								ol.setShippingCostAmount(new BigDecimal(0));
							}
						}
					}
				}
			}
		}
	}

	private void calculateOrderLines() {
		for (OrderLineDto ol : order.getOrderLines()) {
			if (ol.getPayWithVoucher() == null || !ol.getPayWithVoucher()) {
				BigDecimal productTaxPercentage = ol.getOrderProduct().getTaxPercentage();

				BigDecimal nettoDiscountedAmount = ol.getNettoAmount().subtract(ol.getDiscountAmount());

				BigDecimal totalOrderLineTaxAmount = nettoDiscountedAmount.multiply(productTaxPercentage).divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN);

				ol.setTotalTaxAmount(totalOrderLineTaxAmount);

				BigDecimal totalOrderLineAmount = nettoDiscountedAmount.add(totalOrderLineTaxAmount);
				totalOrderLineAmount = totalOrderLineAmount.setScale(2, RoundingMode.HALF_EVEN);

				ol.setTotalAmount(totalOrderLineAmount);

				Money totalOrderLineTaxAmountMoney = Money.of(ol.getTotalTaxAmount(), currency);
				Money totalOrderLineDiscountsAmountMoney = Money.of(ol.getDiscountAmount(), currency);
				Money totalOrderLineAmountMoney = Money.of(ol.getTotalAmount(), currency);

				totalOrderTaxAmount = totalOrderTaxAmount.add(totalOrderLineTaxAmountMoney);
				totalOrderDiscountsAmount = totalOrderDiscountsAmount.add(totalOrderLineDiscountsAmountMoney);

				if (totalTaxesMap.containsKey(productTaxPercentage)) {
					totalTaxesMap.put(productTaxPercentage, totalTaxesMap.get(productTaxPercentage).add(totalOrderLineTaxAmountMoney));
				} else {
					totalTaxesMap.put(productTaxPercentage, totalOrderLineTaxAmountMoney);
				}

				totalOrderLinesGrossAmount = totalOrderLinesGrossAmount.add(totalOrderLineAmountMoney);

				if (ol.getOrderProduct().getPaymentScheduleId() != null) {
					for (OrderLinePaymentScheduleDto olps : ol.getOrderLinePaymentSchedules()) {
						BigDecimal olpsNettoDiscountedAmount = olps.getNettoAmount().subtract(olps.getDiscountAmount());

						BigDecimal olpsTotalTaxAmount = olpsNettoDiscountedAmount.multiply(productTaxPercentage).divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN);

						olps.setTotalTaxAmount(olpsTotalTaxAmount);

						BigDecimal olpsTotalAmount = olpsNettoDiscountedAmount.add(olpsTotalTaxAmount);
						olpsTotalAmount = olpsTotalAmount.setScale(2, RoundingMode.HALF_EVEN);

						olps.setTotalAmount(totalOrderLineAmount);
					}
				}
			}
		}
	}

	private void calculateOrderPaymentMethodCosts() {
		if (order.getOrderPaymentMethod() != null) {
			if (order.getOrderPaymentMethod().getAdministrativeCostsFixed() == null
					&& order.getOrderPaymentMethod().getAdministrativeCostsPercentage() == null) {
				throw new BadRequestException("Payment Method not valid");
			}

			if (order.getOrderPaymentMethod().getAdministrativeCostsFixed() != null) {
				administrativeCosts = Money.of(order.getOrderPaymentMethod().getAdministrativeCostsFixed(), currency);
			} else if (order.getOrderPaymentMethod().getAdministrativeCostsPercentage() != null) {
				if (totalOrderAmount.getNumberStripped().compareTo(new BigDecimal(0)) == 0) {
					administrativeCosts = Money.of(0, currency);
				} else {
					administrativeCosts = Money.of(totalOrderAmount.getNumberStripped()
							.multiply(order.getOrderPaymentMethod().getAdministrativeCostsPercentage()).divide(PERCENTAGE, 2, RoundingMode.HALF_EVEN),
							currency);
				}
			}

			totalOrderAmount = totalOrderAmount.add(administrativeCosts);
		}
	}

	public OrderInvoice toInvoice() {
		OrderInvoice oi = new OrderInvoice();

		oi.setCurrency(currency.getCurrencyCode());
		oi.setNoShippingCost(noShippingCost);
		oi.setTotalDiscounts(totalOrderDiscountsAmount.getNumberStripped());
		oi.setTotalProductsGross(totalOrderLinesGrossAmount.getNumberStripped());
		oi.setTotalProductsNetto(totalOrderLinesNettoAmount.getNumberStripped());
		oi.setGrandTotal(totalOrderAmount.getNumberStripped());
		oi.setPaymentMethodCostAmount(administrativeCosts.getNumberStripped());
		oi.setTaxAmount(totalOrderTaxAmount.getNumberStripped());
		oi.setTotalProducts(totalProducts);

		oi.setTotalMoneyVoucher(totalOrderLinesVoucherMoneyAmount.getNumberStripped());
		oi.setTotalPointsVoucher(totalOrderLinesVoucherPointsAmount.getNumberStripped());
		oi.setTotalMinutesVoucher(totalOrderLinesVoucherMinutesAmount.getNumberStripped());

		if (freeItemsProductsMap != null && !freeItemsProductsMap.isEmpty()) {
			if (oi.getFreeItemsLeft() == null) {
				oi.setFreeItemsLeft(new PromotionItemsWrapper());
			}

			oi.getFreeItemsLeft().setProducts(new ArrayList<>());

			for (Map.Entry<Long, Long> product : freeItemsProductsMap.entrySet()) {
				if (product.getValue() > 0) {
					oi.getFreeItemsLeft().getProducts().add(new IdQty(product.getKey(), product.getValue()));
				}
			}
		}

		if (freeItemsAttributeValuesMap != null && !freeItemsAttributeValuesMap.isEmpty()) {
			if (oi.getFreeItemsLeft() == null) {
				oi.setFreeItemsLeft(new PromotionItemsWrapper());
			}

			oi.getFreeItemsLeft().setAttributeValues(new ArrayList<>());

			for (Map.Entry<Long, Long> attributeValue : freeItemsAttributeValuesMap.entrySet()) {
				if (attributeValue.getValue() > 0) {
					oi.getFreeItemsLeft().getAttributeValues().add(new IdQty(attributeValue.getKey(), attributeValue.getValue()));
				}
			}
		}

		oi.setCartOrder(order);

		Map<BigDecimal, BigDecimal> totalTaxesMapInvoice = new HashMap<>();

		for (Map.Entry<BigDecimal, Money> entry : totalTaxesMap.entrySet()) {
			totalTaxesMapInvoice.put(entry.getKey(), entry.getValue().getNumberStripped());
		}

		oi.setTotalTaxesMap(totalTaxesMapInvoice);
		return oi;
	}

	public CartOrderDto getOrder() {
		return order;
	}

	private boolean nullOrEmpty(Collection<?> c) {
		if (c == null || c.isEmpty()) {
			return true;
		}

		return false;
	}

}
