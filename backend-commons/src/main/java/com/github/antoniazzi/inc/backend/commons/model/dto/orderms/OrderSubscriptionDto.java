package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderSubscriptionDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private String invoicePeriod;

	private LocalDate validFrom;

	private LocalDate validTo;

	@NotNull
	private Boolean active;

	private String additionalDetailsJson;

	private List<InvoiceDto> invoices = new ArrayList<>();

	private OrderLineDto orderLine;

}
