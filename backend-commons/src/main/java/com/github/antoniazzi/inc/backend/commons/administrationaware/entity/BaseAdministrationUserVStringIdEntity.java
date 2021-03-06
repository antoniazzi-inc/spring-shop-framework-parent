package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseVStringIdEntity;

/**
 * Base Entity Class to be extended by all Administration User Version Aware
 * Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationUserVStringIdEntity extends BaseVStringIdEntity
		implements AdministrationUserAwareEntity {

	private static final long serialVersionUID = 2724390193112087047L;

	protected Long administrationId;

	protected Long userId;

	public BaseAdministrationUserVStringIdEntity() {

	}

	public BaseAdministrationUserVStringIdEntity(Long administrationId, Long userId) {
		this.administrationId = administrationId;
		this.userId = userId;
	}

	@Override
	public Long getAdministrationId() {
		return administrationId;
	}

	@Override
	public void setAdministrationId(Long administrationId) {
		this.administrationId = administrationId;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}