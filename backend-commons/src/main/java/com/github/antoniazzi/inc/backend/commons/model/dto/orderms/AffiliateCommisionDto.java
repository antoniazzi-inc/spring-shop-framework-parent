package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class AffiliateCommisionDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private Long affiliateRelationId;

	private BigDecimal commission;

	private String agreementDetailsJson;

	private OrderLineDto orderLine;

	private AffiliateRewardDto affiliateReward;

}
