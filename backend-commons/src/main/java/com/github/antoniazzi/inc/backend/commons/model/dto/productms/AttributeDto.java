package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.math.BigDecimal;
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
public class AttributeDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private Boolean multipleValues;

	private Boolean visible;

	private Boolean visibleInFrontEnd;

	private BigDecimal tax;

	private ProductDto product;

	private List<AttributeValueDto> attributeValues = new ArrayList<>();

}
