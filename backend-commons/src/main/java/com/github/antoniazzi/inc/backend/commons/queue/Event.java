package com.github.antoniazzi.inc.backend.commons.queue;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;

	private String action;

	private Object obj;

	public Event() {

	}

	public Event(String type, String action, Object obj) {
		this.type = type;
		this.action = action;
		this.obj = obj;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", action=" + action + ", obj=" + obj + "]";
	}

}
