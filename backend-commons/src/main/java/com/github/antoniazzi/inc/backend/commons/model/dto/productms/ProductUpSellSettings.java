package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProductUpSellSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> cartBanners;

	private List<String> upsellBanners;

	private Map<String, String> settings;

	public List<String> getCartBanners() {
		return cartBanners;
	}

	public void setCartBanners(List<String> cartBanners) {
		this.cartBanners = cartBanners;
	}

	public List<String> getUpsellBanners() {
		return upsellBanners;
	}

	public void setUpsellBanners(List<String> upsellBanners) {
		this.upsellBanners = upsellBanners;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

}
