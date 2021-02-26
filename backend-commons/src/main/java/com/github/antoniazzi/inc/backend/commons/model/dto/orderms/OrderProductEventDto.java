package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderProductEventDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long eventId;

	@NotNull
	private Long courseId;

	private ZonedDateTime eventStart;

	private ZonedDateTime eventEnd;

	private BigDecimal price;

	private BigDecimal voucherValue;

	private Integer seats;

}
