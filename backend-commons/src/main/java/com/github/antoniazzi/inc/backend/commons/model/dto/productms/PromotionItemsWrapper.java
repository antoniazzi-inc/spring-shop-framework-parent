package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

public class PromotionItemsWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	private List<IdQty> products;

	@Valid
	private List<IdQty> attributeValues;

	public List<IdQty> getProducts() {
		return products;
	}

	public void setProducts(List<IdQty> products) {
		this.products = products;
	}

	public List<IdQty> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(List<IdQty> attributeValues) {
		this.attributeValues = attributeValues;
	}

}
