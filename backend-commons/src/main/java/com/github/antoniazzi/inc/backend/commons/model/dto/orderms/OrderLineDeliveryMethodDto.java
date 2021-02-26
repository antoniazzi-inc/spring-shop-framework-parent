package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderLineDeliveryMethodDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long deliveryMethodId;

	private String name;

	private String detailsJson;

	private OrderLineDto orderLine;

}
