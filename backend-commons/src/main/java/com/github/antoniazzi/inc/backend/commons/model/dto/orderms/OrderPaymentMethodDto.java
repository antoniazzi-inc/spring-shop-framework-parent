package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderPaymentMethodDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long paymentMethodId;

	private BigDecimal administrativeCostsPercentage;

	private BigDecimal administrativeCostsFixed;

	private String name;

	private String detailsJson;

	private CartOrderDto cartOrder;

}
