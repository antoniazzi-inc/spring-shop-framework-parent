package com.github.antoniazzi.inc.backend.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class BaseAdministrationEntityDto extends BaseEntityDto {

	private static final long serialVersionUID = 1L;

	protected Long administrationId;

}
