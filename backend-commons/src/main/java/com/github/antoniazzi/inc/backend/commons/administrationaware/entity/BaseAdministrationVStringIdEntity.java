package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseVStringIdEntity;

/**
 * Base Entity Class to be extended by all Administration Version Aware Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationVStringIdEntity extends BaseVStringIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = -4780000194475910010L;

	protected Long administrationId;

	public BaseAdministrationVStringIdEntity() {

	}

	public BaseAdministrationVStringIdEntity(Long administrationId) {
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