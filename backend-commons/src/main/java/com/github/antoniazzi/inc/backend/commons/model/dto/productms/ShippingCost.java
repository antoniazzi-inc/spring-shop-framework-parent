package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShippingCost implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long regionId;
	private Long preferredShippingMethodId;

	private BigDecimal cost;

	private Integer indexOrder;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getPreferredShippingMethodId() {
		return preferredShippingMethodId;
	}

	public void setPreferredShippingMethodId(Long preferredShippingMethodId) {
		this.preferredShippingMethodId = preferredShippingMethodId;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Integer getIndexOrder() {
		return indexOrder;
	}

	public void setIndexOrder(Integer indexOrder) {
		this.indexOrder = indexOrder;
	}

}
