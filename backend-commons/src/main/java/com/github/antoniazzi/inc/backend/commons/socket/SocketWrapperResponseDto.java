package com.github.antoniazzi.inc.backend.commons.socket;

import com.github.antoniazzi.inc.backend.commons.model.dto.response.BaseResponseDto;

public class SocketWrapperResponseDto extends BaseResponseDto {

	private static final long serialVersionUID = 1L;

	private Object content;

	private SocketAction action;

	private String type;

	private String status;

	public SocketWrapperResponseDto(Object content) {
		this.content = content;
	}

	public SocketWrapperResponseDto(Object content, String type) {
		this.content = content;
		this.type = type;
	}

	public SocketWrapperResponseDto(Object content, SocketAction action, String type) {
		this.content = content;
		this.action = action;
		this.type = type;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public SocketAction getAction() {
		return action;
	}

	public void setAction(SocketAction action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
