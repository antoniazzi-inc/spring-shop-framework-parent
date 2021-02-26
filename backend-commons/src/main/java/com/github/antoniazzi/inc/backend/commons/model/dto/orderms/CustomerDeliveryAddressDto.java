package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationAddressEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class CustomerDeliveryAddressDto extends BaseRelationAdministrationAddressEntityDto {

	private static final long serialVersionUID = 1L;

	private Long companyId;

	@NotNull
	private Long relationAddressId;

	private String countryName;

	private CartOrderDto cartOrder;

}
