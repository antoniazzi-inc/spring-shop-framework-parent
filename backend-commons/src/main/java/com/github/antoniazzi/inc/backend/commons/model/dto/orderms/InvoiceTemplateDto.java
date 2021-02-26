package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class InvoiceTemplateDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private Boolean isDefault;

	private String logoUrl;

	private String templateDataJson;

	private List<InvoiceDto> invoices = new ArrayList<>();

}
