package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultiLanguage implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 8)
	protected String langKey;

	@NotNull
	@Size(max = 254)
	protected String name;

	@Size(max = 1024)
	protected String description;

}
