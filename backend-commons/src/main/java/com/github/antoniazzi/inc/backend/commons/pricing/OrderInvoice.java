package com.github.antoniazzi.inc.backend.commons.pricing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.github.antoniazzi.inc.backend.commons.model.dto.orderms.CartOrderDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.PromotionItemsWrapper;

import lombok.Data;

@Data
public class OrderInvoice {

	private String currency;

	private Integer totalProducts;

	private BigDecimal paymentMethodCostAmount;
	private BigDecimal totalProductsNetto;
	private BigDecimal totalProductsGross;
	private BigDecimal totalDiscounts;
	private BigDecimal grandTotal;
	private BigDecimal taxAmount;

	private BigDecimal totalMoneyVoucher;
	private BigDecimal totalPointsVoucher;
	private BigDecimal totalMinutesVoucher;

	private boolean noShippingCost = false;

	private Map<BigDecimal, BigDecimal> totalTaxesMap = new HashMap<>();

	private PromotionItemsWrapper freeItemsLeft;

	private CartOrderDto cartOrder;

}
