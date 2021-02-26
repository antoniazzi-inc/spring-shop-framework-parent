package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class AffiliateRewardDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private Long affiliateRelationId;

	private ZonedDateTime paymentDate;

	private BigDecimal nettoAmount;

	private BigDecimal taxAmount;

	private BigDecimal totalAmount;

	private CreditInvoiceDto creditInvoice;

	private List<AffiliateCommisionDto> affiliateCommisions = new ArrayList<>();

}
