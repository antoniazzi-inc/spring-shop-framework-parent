package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.productms.PromotionItemsWrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class OrderDiscountLineDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long discountId;

	private BigDecimal percentage;

	private BigDecimal fixed;

	private Boolean noShipping;

	@Valid
	private PromotionItemsWrapper freeItemsJson;

	private Boolean entireOrder;

	@Valid
	@NotNull
	private OrderPromotionDto orderPromotion;

	private CartOrderDto cartOrder;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDiscountLineDto other = (OrderDiscountLineDto) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

}
