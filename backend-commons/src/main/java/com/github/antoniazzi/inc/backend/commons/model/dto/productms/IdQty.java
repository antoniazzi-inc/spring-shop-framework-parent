package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class IdQty implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;

	@NotNull
	private Long quantity;

	public IdQty() {
	}

	public IdQty(@NotNull Long id, @NotNull Long quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
