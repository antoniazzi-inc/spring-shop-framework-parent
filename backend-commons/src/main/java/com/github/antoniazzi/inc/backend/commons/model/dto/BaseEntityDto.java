package com.github.antoniazzi.inc.backend.commons.model.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class BaseEntityDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	protected Long id;

	protected ZonedDateTime createdOn;

	protected ZonedDateTime updatedOn;

	protected Long version;

}
