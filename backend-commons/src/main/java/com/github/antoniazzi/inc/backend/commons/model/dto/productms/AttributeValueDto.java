package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class AttributeValueDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer orderIndex;

	@NotNull
	private BigDecimal price;

	private Integer stock;

	@NotNull
	@Size(max = 254)
	private String value;

	private BigDecimal voucherValue;

	private AttributeDto attribute;

}
