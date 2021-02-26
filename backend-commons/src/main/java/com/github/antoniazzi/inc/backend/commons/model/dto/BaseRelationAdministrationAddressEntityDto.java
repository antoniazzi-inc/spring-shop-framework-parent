package com.github.antoniazzi.inc.backend.commons.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class BaseRelationAdministrationAddressEntityDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 254)
	protected String street;

	@NotNull
	@Size(max = 10)
	protected String houseNumber;

	@NotNull
	@Size(max = 120)
	protected String city;

	@NotNull
	protected Long countryId;

	@Size(max = 10)
	protected String entranceNumber;

	@Size(max = 10)
	protected String appartmentNumber;

	@NotNull
	@Size(max = 10)
	protected String postalCode;

	@NotNull
	protected String addressType;

	@Size(max = 254)
	protected String phoneNumber;

	@Size(max = 254)
	protected String description;

	protected String phoneType;

}
