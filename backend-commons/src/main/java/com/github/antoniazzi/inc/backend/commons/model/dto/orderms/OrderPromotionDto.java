package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.PromotionType;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.IdDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderPromotionDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long promotionId;

	private PromotionType promotionType;

	private String name;

	private String description;

	private ZonedDateTime availableFrom;

	private ZonedDateTime availableTo;

	private Boolean recurrent;

	private String promotionTypeDetailsJson;

	private OrderDiscountLineDto orderDiscountLine;

	private List<IdDto> products = new ArrayList<>();

	private List<IdDto> attributeValues = new ArrayList<>();

	private List<IdDto> promotionProductCategories = new ArrayList<>();

}
