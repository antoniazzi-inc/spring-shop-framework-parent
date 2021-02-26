package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.orderms.PaymentStatus;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class PaymentEventDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private PaymentStatus paymentEventType;

	private BigDecimal amount;

	private String transactionCode;

	private String detailsJson;

	private String batchId;

	private InvoiceDto invoice;

	private CreditInvoiceDto creditInvoice;

}
