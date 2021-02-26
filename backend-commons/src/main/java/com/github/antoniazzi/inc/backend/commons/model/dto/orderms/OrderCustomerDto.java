package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderCustomerDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long relationId;

	@Size(max = 254)
	private String email;

	private String fullName;

	@Size(max = 254)
	private String title;

	@Size(max = 50)
	private String vatNumber;

	private Boolean isCompany;

	@Size(max = 254)
	private String companyName;

	private String taxRulesJson;

	private CartOrderDto cartOrder;

}
