package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;

public class Announcement implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sendBeforeNew;
	private BaseEmail email;

	public String getSendBeforeNew() {
		return sendBeforeNew;
	}

	public void setSendBeforeNew(String sendBeforeNew) {
		this.sendBeforeNew = sendBeforeNew;
	}

	public BaseEmail getEmail() {
		return email;
	}

	public void setEmail(BaseEmail email) {
		this.email = email;
	}

}
