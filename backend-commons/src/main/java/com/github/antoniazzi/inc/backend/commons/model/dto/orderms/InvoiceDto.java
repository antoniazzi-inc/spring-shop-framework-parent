package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.orderms.InvoiceType;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class InvoiceDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private InvoiceType invoiceType;

	private String fullName;

	private String email;

	private String invoiceNumber;

	private Boolean paid;

	private BigDecimal nettoAmount;

	private BigDecimal discountAmount;

	private BigDecimal taxAmount;

	private BigDecimal paymentMethodCostAmount;

	private BigDecimal shippingCostAmount;

	private BigDecimal totalAmount;

	private Boolean taxDisabled;

	private Boolean taxReverseCharge;

	private String additionalDetailsJson;

	private ZonedDateTime scheduledOn;

	private ZonedDateTime sentOn;

	private Integer dueDays;

	private List<PaymentEventDto> paymentEvents = new ArrayList<>();

	private List<CreditInvoiceDto> creditInvoices = new ArrayList<>();

	private CartOrderDto order;

	private OrderLinePaymentScheduleDto orderLinePaymentSchedule;

	private InvoiceTemplateDto invoiceTemplate;

	private OrderSubscriptionDto orderSubscription;

}
