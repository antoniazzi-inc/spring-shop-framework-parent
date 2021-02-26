package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.VoucherType;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderProductPurchasedVoucherDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private Long typeVoucherId;

	private Long beneficiaryRelationId;

	private BigDecimal value;

	private VoucherType voucherType;

	private Integer daysValid;

	private ZonedDateTime availableFrom = ZonedDateTime.now();

	private ZonedDateTime availableTo;

}
