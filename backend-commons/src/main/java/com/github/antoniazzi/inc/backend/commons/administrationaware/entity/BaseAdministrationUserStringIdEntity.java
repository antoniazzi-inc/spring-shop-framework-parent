package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseStringIdEntity;

/**
 * Base Entity Class to be extended by all Administration User Aware Entities
 * 
 * @version 1.0.0
 * @since 13.11.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationUserStringIdEntity extends BaseStringIdEntity implements AdministrationUserAwareEntity {

	private static final long serialVersionUID = -8136652233962595788L;

	protected Long administrationId;

	protected Long userId;

	public BaseAdministrationUserStringIdEntity() {

	}

	public BaseAdministrationUserStringIdEntity(Long administrationId, Long userId) {
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