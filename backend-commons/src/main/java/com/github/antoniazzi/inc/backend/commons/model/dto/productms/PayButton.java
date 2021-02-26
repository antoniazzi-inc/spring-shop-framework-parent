package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class PayButton implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean emptyCart;
	private String buttonName;
	private String shoppingCartLanguage;
	private String fontName;
	private String fontUrl;
	private String fontColor;
	private Integer fontSize;
	private Boolean isBold;
	private Boolean isItalic;
	private String backgroundColor;
	private String borderColor;
	private Integer borderThickness;
	private String borderStyle;
	private String shadowColor;
	private String shadowSize;
	private Integer width;
	private Integer height;
	private Integer roundCorners;
	private String imageUrl;
	private String imageSettings;

	public Boolean getEmptyCart() {
		return emptyCart;
	}

	public void setEmptyCart(Boolean emptyCart) {
		this.emptyCart = emptyCart;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getShoppingCartLanguage() {
		return shoppingCartLanguage;
	}

	public void setShoppingCartLanguage(String shoppingCartLanguage) {
		this.shoppingCartLanguage = shoppingCartLanguage;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getFontUrl() {
		return fontUrl;
	}

	public void setFontUrl(String fontUrl) {
		this.fontUrl = fontUrl;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Boolean getIsBold() {
		return isBold;
	}

	public void setIsBold(Boolean isBold) {
		this.isBold = isBold;
	}

	public Boolean getIsItalic() {
		return isItalic;
	}

	public void setIsItalic(Boolean isItalic) {
		this.isItalic = isItalic;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public Integer getBorderThickness() {
		return borderThickness;
	}

	public void setBorderThickness(Integer borderThickness) {
		this.borderThickness = borderThickness;
	}

	public String getShadowColor() {
		return shadowColor;
	}

	public void setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getRoundCorners() {
		return roundCorners;
	}

	public void setRoundCorners(Integer roundCorners) {
		this.roundCorners = roundCorners;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageSettings() {
		return imageSettings;
	}

	public void setImageSettings(String imageSettings) {
		this.imageSettings = imageSettings;
	}

	public String getBorderStyle() {
		return borderStyle;
	}

	public void setBorderStyle(String borderStyle) {
		this.borderStyle = borderStyle;
	}

	public String getShadowSize() {
		return shadowSize;
	}

	public void setShadowSize(String shadowSize) {
		this.shadowSize = shadowSize;
	}

	@Override
	public String toString() {
		return "PayButton [emptyCart=" + emptyCart + ", buttonName=" + buttonName + ", shoppingCartLanguage="
				+ shoppingCartLanguage + ", fontName=" + fontName + ", fontUrl=" + fontUrl + ", fontColor=" + fontColor
				+ ", fontSize=" + fontSize + ", isBold=" + isBold + ", isItalic=" + isItalic + ", backgroundColor="
				+ backgroundColor + ", borderColor=" + borderColor + ", borderThickness=" + borderThickness
				+ ", shadowColor=" + shadowColor + ", width=" + width + ", height=" + height + ", roundCorners="
				+ roundCorners + ", imageUrl=" + imageUrl + ", imageSettings=" + imageSettings + "]";
	}

}
