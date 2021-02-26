package com.github.antoniazzi.inc.backend.commons.pricing;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.PaymentSchedulePeriod;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ProductType;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.VoucherSupport;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.CartOrderDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderLineDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderLinePaymentScheduleDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderProductAttributeValueDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderProductDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderProductEventDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.OrderProductPurchasedVoucherDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.AttributeValueDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.EventDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.PaymentScheduleDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.PaymentScheduleOptionDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.ProductDto;
import com.github.antoniazzi.inc.backend.commons.util.H;

public class OrderEngine {

	private final CartOrderDto order;

	private final ZonedDateTime now = ZonedDateTime.now();

	private String relationsQuery;
	private List<Long> relationIds = new ArrayList<>();

	public OrderEngine(CartOrderDto order) {
		this.order = order;

		iterateRelations();
		iterateProductsAndAttributeValues();
	}

	// TODO change JsonNode with AdministrationDto when available
	public CartOrderDto applyAdministrationData(JsonNode administration) {
		order.setCurrency(administration.get("currency").asText());

		return order;
	}

	private void iterateRelations() {
		if (order.getOrderCustomer() != null && order.getOrderCustomer().getRelationId() != null) {
			// first always the customer id
			relationIds.add(order.getOrderCustomer().getRelationId());
		}

		if (H.NullOrEmpty(order.getOrderLines())) {
			// check for beneficiaries and add their ids to the list
			relationIds.addAll(order.getOrderLines().stream().filter(e -> {
				if (e.getOrderLineBeneficiary() != null)
					return true;
				return false;
			}).map(e -> {
				return e.getOrderLineBeneficiary().getBeneficiaryRelationId();
			}).collect(Collectors.toList()));

			// remove duplicates
			relationIds = new ArrayList<>(new HashSet<>(relationIds));
		}

		if (!H.NullOrEmpty(relationIds)) {
			relationsQuery = "id=in=(";

			for (int i = 0; i < relationIds.size(); i++) {
				relationsQuery += relationIds.get(i);

				if (i != relationIds.size() - 1) {
					relationsQuery += ",";
				}
			}

			relationsQuery += ")";
		}
	}

	private void iterateProductsAndAttributeValues() {
		if (H.NullOrEmpty(order.getOrderLines())) {

		}
	}

	public CartOrderDto applyProductsData(List<ProductDto> products) {
		if (!H.NullOrEmpty(order.getOrderLines()) && !H.NullOrEmpty(products)) {
			for (OrderLineDto ol : order.getOrderLines()) {
				for (ProductDto p : products) {
					if (ol.getOrderProduct().getProductId() == p.getId()) {
						applyProductFields(ol.getOrderProduct(), p);
						handleProductTypes(ol, p);
						handlePaymentSchedules(ol, p);
					}
				}
			}
		}

		return order;
	}

	private CartOrderDto handlePaymentSchedules(OrderLineDto ol, ProductDto p) {
		if (ol.getOrderProduct().getPaymentScheduleId() != null) {
			if (!p.getVoucherSupport().equals(VoucherSupport.NONE) && ol.getPayWithVoucher() != null
					&& ol.getPayWithVoucher()) {
				throw new BadRequestException("Payment Schedules not supported with Voucher Payments");
			}

			if (H.NullOrEmpty(p.getPaymentSchedules())) {
				throw new BadRequestException("Payment Schedules not supported");
			}

			PaymentScheduleDto paymentSchedule = null;

			for (PaymentScheduleDto ps : p.getPaymentSchedules()) {
				if (ol.getOrderProduct().getPaymentScheduleId() == ps.getId()) {
					paymentSchedule = ps;
				}
			}

			if (paymentSchedule == null || H.NullOrEmpty(paymentSchedule.getPaymentScheduleOptions())) {
				throw new BadRequestException("Payment Schedule not valid");
			}

			ol.setOrderLinePaymentSchedules(new ArrayList<>());

			for (int i = 0; i < paymentSchedule.getPaymentScheduleOptions().size(); i++) {
				PaymentScheduleOptionDto pso = paymentSchedule.getPaymentScheduleOptions().get(i);

				OrderLinePaymentScheduleDto orderLinePaymentSchedule = new OrderLinePaymentScheduleDto();

				orderLinePaymentSchedule.setNettoAmount(pso.getPrice());

				if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.DAY)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusDays(Long.valueOf(i)).toLocalDate());
				} else if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.WEEK)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusWeeks(Long.valueOf(i)).toLocalDate());
				} else if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.BI_WEEK)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusDays(Long.valueOf(i * 2)).toLocalDate());
				} else if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.MONTH)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusMonths(Long.valueOf(i)).toLocalDate());
				} else if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.QUARTER)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusMonths(Long.valueOf(i * 4)).toLocalDate());
				} else if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.HALF_YEAR)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusMonths(Long.valueOf(i * 6)).toLocalDate());
				} else if (paymentSchedule.getPeriod().equals(PaymentSchedulePeriod.YEAR)) {
					orderLinePaymentSchedule.setPaymentDate(now.plusYears(Long.valueOf(i)).toLocalDate());
				}

				ol.getOrderLinePaymentSchedules().add(orderLinePaymentSchedule);
			}
		}

		return order;
	}

	private OrderProductDto applyProductFields(OrderProductDto orderProduct, ProductDto p) {
		orderProduct.setId(null);
		orderProduct.setProductPrice(p.getPrice());
		orderProduct.setTaxPercentage(p.getTax());
		orderProduct.setPoints(p.getPoints());
		orderProduct.setProductType(p.getProductType());
		orderProduct.setVoucherSupport(p.getVoucherSupport());
		orderProduct.setVoucherValue(p.getVoucherValue());

		return orderProduct;
	}

	private void handleProductTypes(OrderLineDto ol, ProductDto p) {
		if (p.getProductType().equals(ProductType.DIGITAL)) {
			if (p.getTypeDigital() == null || p.getTypeDigital().getUrl() == null) {
				throw new BadRequestException("TypeDigital not valid");
			}

			ol.getOrderProduct().setDownloadUrl(p.getTypeDigital().getUrl());
		} else if (p.getProductType().equals(ProductType.PHYSICAL)) {
			// TODO shipping costs
		} else if (p.getProductType().equals(ProductType.SERVICE)) {
			if (p.getTypeService() == null) {
				throw new BadRequestException("TypeService not provided");
			}

			ol.getOrderProduct().setPriceType(p.getTypeService().getPriceType());
		} else if (p.getProductType().equals(ProductType.COURSE)) {
			if (ol.getOrderProduct().getOrderProductEvent() == null) {
				throw new BadRequestException("ProductEvent not provided");
			}

		} else if (p.getProductType().equals(ProductType.VOUCHER)) {
			if (p.getTypeVoucher() == null) {
				throw new BadRequestException("TypeVoucher not provided");
			}

			OrderProductPurchasedVoucherDto purchasedVoucher;

			if (ol.getOrderProduct().getOrderProductPurchasedVoucher() == null) {
				purchasedVoucher = new OrderProductPurchasedVoucherDto();
			} else {
				purchasedVoucher = ol.getOrderProduct().getOrderProductPurchasedVoucher();
			}

			purchasedVoucher.setId(null);
			purchasedVoucher.setAvailableFrom(now);
			purchasedVoucher.setValue(p.getTypeVoucher().getValue());
			purchasedVoucher.setTypeVoucherId(p.getTypeVoucher().getId());
			purchasedVoucher.setVoucherType(p.getTypeVoucher().getVoucherType());

			if (p.getTypeVoucher().getDaysValid() != null) {
				purchasedVoucher.setAvailableTo(now.plusDays(p.getTypeVoucher().getDaysValid()));
			}

			ol.getOrderProduct().setOrderProductPurchasedVoucher(purchasedVoucher);
		}
	}

	public CartOrderDto applyAttributeValuesData(List<AttributeValueDto> attributeValues) {
		if (!H.NullOrEmpty(order.getOrderLines())) {
			for (OrderLineDto ol : order.getOrderLines()) {
				if (ol.getOrderProduct() != null
						&& !H.NullOrEmpty(ol.getOrderProduct().getOrderProductAttributeValues())) {
					for (OrderProductAttributeValueDto orderProductAttributeValue : ol.getOrderProduct()
							.getOrderProductAttributeValues()) {
						for (AttributeValueDto av : attributeValues) {
							if (orderProductAttributeValue.getAttributeValueId() == av.getId()) {
								applyAttributeValueFields(orderProductAttributeValue, av);
							}
						}
					}
				}
			}
		}

		return order;
	}

	private OrderProductAttributeValueDto applyAttributeValueFields(
			OrderProductAttributeValueDto orderProductAttributeValue, AttributeValueDto av) {
		orderProductAttributeValue.setId(null);

		if (av.getAttribute() != null) {
			orderProductAttributeValue.setAttributeId(av.getAttribute().getId());
		}

		orderProductAttributeValue.setAttributeValuePrice(av.getPrice());
		orderProductAttributeValue.setAttributeValueVoucherValue(av.getVoucherValue());

		return orderProductAttributeValue;
	}

	public CartOrderDto applyEventsData(List<EventDto> events) {
		if (!H.NullOrEmpty(order.getOrderLines())) {
			for (OrderLineDto ol : order.getOrderLines()) {
				if (ol.getOrderProduct().getProductType().equals(ProductType.COURSE)
						&& ol.getOrderProduct().getOrderProductEvent() != null
						&& ol.getOrderProduct().getOrderProductEvent().getEventId() != null) {
					for (EventDto e : events) {
						if (ol.getOrderProduct().getOrderProductEvent().getEventId() == e.getId()) {
							applyEventFields(ol.getOrderProduct().getOrderProductEvent(), e);
						}
					}
				}
			}
		}
		return order;
	}

	private OrderProductEventDto applyEventFields(OrderProductEventDto orderProductEvent, EventDto e) {
		orderProductEvent.setId(null);
		orderProductEvent.setPrice(e.getPrice());
		orderProductEvent.setEventEnd(e.getEventEnd());
		orderProductEvent.setEventStart(e.getEventStart());
		orderProductEvent.setVoucherValue(e.getVoucherValue());

		if (e.getCourse() != null) {
			orderProductEvent.setCourseId(e.getCourse().getId());
		}

		return orderProductEvent;
	}

	public CartOrderDto getOrder() {
		return order;
	}

	public String getRelationsQuery() {
		return relationsQuery;
	}

	public List<Long> getRelationIds() {
		return relationIds;
	}

}
