package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.IdDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class EventDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@Max(90)
	@Min(-90)
	private Double latitude;

	@Max(180)
	@Min(-180)
	private Double longitude;

	private Integer seats;

	private ZonedDateTime eventStart;

	private ZonedDateTime eventEnd;

	private BigDecimal price;

	private BigDecimal voucherValue;

	private IdDto course;

}
