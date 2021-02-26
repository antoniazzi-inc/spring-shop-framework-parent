package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.orderms.OrderDeliveryStatus;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class OrderLineDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private OrderDeliveryStatus orderDeliveryStatus;

	@NotNull
	private Integer quantity;

	private BigDecimal nettoAmount;

	private BigDecimal discountAmount;

	private BigDecimal shippingCostAmount;

	private BigDecimal totalTaxAmount;

	private BigDecimal totalAmount;

	private String additionalInfo;

	private Boolean payWithVoucher;

	private Boolean isFreeItem;

	@Valid
	private OrderSubscriptionDto orderSubscription;

	@Valid
	@NotNull
	private OrderProductDto orderProduct;

	@Valid
	private BeneficiaryDeliveryAddressDto beneficiaryDeliveryAddress;

	@Valid
	private OrderLineBeneficiaryDto orderLineBeneficiary;

	@Valid
	private OrderLineDeliveryMethodDto orderLineDeliveryMethod;

	private List<OrderLinePaymentScheduleDto> orderLinePaymentSchedules = new ArrayList<>();

	private List<AffiliateCommisionDto> affiliateCommisions = new ArrayList<>();

	private CartOrderDto cartOrder;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLineDto other = (OrderLineDto) obj;
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
