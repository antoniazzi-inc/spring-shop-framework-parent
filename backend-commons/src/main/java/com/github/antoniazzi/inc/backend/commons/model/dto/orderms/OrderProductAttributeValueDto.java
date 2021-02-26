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
public class OrderProductAttributeValueDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long attributeId;

	@NotNull
	private Long attributeValueId;

	private String attributeName;

	private String attributeDescription;

	private String attributeValueName;

	private String attributeValueDescription;

	private BigDecimal attributeValuePrice;

	private BigDecimal attributeValueVoucherValue;

	private OrderProductDto orderProduct;

}
