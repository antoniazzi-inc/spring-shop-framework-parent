package com.github.antoniazzi.inc.backend.commons.model.dto;

import com.github.antoniazzi.inc.backend.commons.entity.enumeration.InvoiceStrategyMechanism;
import com.github.antoniazzi.inc.backend.commons.model.BaseModel;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvoiceStrategy extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotNull
	private InvoiceStrategyMechanism mechanism = InvoiceStrategyMechanism.TEXT;

	@NotNull
	private String text;

	private Long offset = 0l;

	private InvoiceStrategyMechanism newMechanism = InvoiceStrategyMechanism.TEXT;

	private String newText;

	private Long newOffset;

}
