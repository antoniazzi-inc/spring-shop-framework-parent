package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ServicePriceType;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class TypeServiceDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private ServicePriceType priceType;

}
