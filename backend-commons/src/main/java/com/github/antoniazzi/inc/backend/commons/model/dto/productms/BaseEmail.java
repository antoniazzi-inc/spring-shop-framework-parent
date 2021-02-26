package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;

public class BaseEmail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sendFromName;
	private String sendToAddress;
	private String sendFromAddress;

	private String replyToName;
	private String replyToAddress;

	private String subject;
	private String content;

	public String getSendFromName() {
		return sendFromName;
	}

	public void setSendFromName(String sendFromName) {
		this.sendFromName = sendFromName;
	}

	public String getSendToAddress() {
		return sendToAddress;
	}

	public void setSendToAddress(String sendToAddress) {
		this.sendToAddress = sendToAddress;
	}

	public String getSendFromAddress() {
		return sendFromAddress;
	}

	public void setSendFromAddress(String sendFromAddress) {
		this.sendFromAddress = sendFromAddress;
	}

	public String getReplyToName() {
		return replyToName;
	}

	public void setReplyToName(String replyToName) {
		this.replyToName = replyToName;
	}

	public String getReplyToAddress() {
		return replyToAddress;
	}

	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
