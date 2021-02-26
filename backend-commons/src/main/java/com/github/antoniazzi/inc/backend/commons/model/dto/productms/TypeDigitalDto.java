package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class TypeDigitalDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private String url;

}
