package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ReservationStatus;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class EventReservationDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private Boolean isPaid;

	private Long relationId;

	private ReservationStatus reservationStatus;

}
