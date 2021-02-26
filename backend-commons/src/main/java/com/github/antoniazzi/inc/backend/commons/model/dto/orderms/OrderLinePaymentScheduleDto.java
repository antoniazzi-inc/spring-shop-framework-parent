package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderLinePaymentScheduleDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private LocalDate reminderDate;

	private LocalDate paymentDate;

	private Integer quantity;

	private BigDecimal nettoAmount;

	private BigDecimal discountAmount;

	private BigDecimal shippingCostAmount;

	private BigDecimal totalTaxAmount;

	private BigDecimal totalAmount;

	private BigDecimal taxPercentage;

	private InvoiceDto invoice;

	private OrderLineDto orderLine;

}
