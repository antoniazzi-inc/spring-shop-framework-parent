package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductThankYouPageSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	private String redirectUrl;
	private List<String> dataToSendWithRedirectUrl; // e.g. invoice number, product id, order id

	private String thankyouContent;

	private Boolean sellToFriendsEnabled;
	private BigDecimal sellToFriendsRewardFixed;
	private BigDecimal sellToFriendsRewardPercentage;

	private Map<String, String> settings;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public List<String> getDataToSendWithRedirectUrl() {
		return dataToSendWithRedirectUrl;
	}

	public void setDataToSendWithRedirectUrl(List<String> dataToSendWithRedirectUrl) {
		this.dataToSendWithRedirectUrl = dataToSendWithRedirectUrl;
	}

	public String getThankyouContent() {
		return thankyouContent;
	}

	public void setThankyouContent(String thankyouContent) {
		this.thankyouContent = thankyouContent;
	}

	public Boolean getSellToFriendsEnabled() {
		return sellToFriendsEnabled;
	}

	public void setSellToFriendsEnabled(Boolean sellToFriendsEnabled) {
		this.sellToFriendsEnabled = sellToFriendsEnabled;
	}

	public BigDecimal getSellToFriendsRewardFixed() {
		return sellToFriendsRewardFixed;
	}

	public void setSellToFriendsRewardFixed(BigDecimal sellToFriendsRewardFixed) {
		this.sellToFriendsRewardFixed = sellToFriendsRewardFixed;
	}

	public BigDecimal getSellToFriendsRewardPercentage() {
		return sellToFriendsRewardPercentage;
	}

	public void setSellToFriendsRewardPercentage(BigDecimal sellToFriendsRewardPercentage) {
		this.sellToFriendsRewardPercentage = sellToFriendsRewardPercentage;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

}
