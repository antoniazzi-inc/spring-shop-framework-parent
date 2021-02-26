package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProductRegistrationSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jsonDefinedFields; // list of fields, required or not, ordering and grouping. Perhaps better to
	// define dedicated dto...

	private List<String> fieldsIncludedInInvoice; // list of registration fields that should also be shown in the
	// invoice after the purchase

	private Map<String, String> settings;

	public String getJsonDefinedFields() {
		return jsonDefinedFields;
	}

	public void setJsonDefinedFields(String jsonDefinedFields) {
		this.jsonDefinedFields = jsonDefinedFields;
	}

	public List<String> getFieldsIncludedInInvoice() {
		return fieldsIncludedInInvoice;
	}

	public void setFieldsIncludedInInvoice(List<String> fieldsIncludedInInvoice) {
		this.fieldsIncludedInInvoice = fieldsIncludedInInvoice;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

}
