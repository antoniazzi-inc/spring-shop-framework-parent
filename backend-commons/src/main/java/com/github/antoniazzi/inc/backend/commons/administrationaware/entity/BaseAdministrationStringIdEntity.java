package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseStringIdEntity;

/**
 * Base Entity Class to be extended by all Administration Aware Entities
 * 
 * @version 1.0.0
 * @since 13.11.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationStringIdEntity extends BaseStringIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = -2251768220745873134L;

	protected Long administrationId;

	public BaseAdministrationStringIdEntity() {

	}

	public BaseAdministrationStringIdEntity(Long administrationId) {
		this.administrationId = administrationId;
	}

	@Override
	public Long getAdministrationId() {
		return administrationId;
	}

	@Override
	public void setAdministrationId(Long administrationId) {
		this.administrationId = administrationId;
	}

}