package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseIdEntity;

/**
 * Base Entity Class to be extended by all Administration Aware Entities
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationIdEntity extends BaseIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = 6161155141287383983L;

	@Column(name = "administration_id")
	protected Long administrationId;

	public BaseAdministrationIdEntity() {

	}

	public BaseAdministrationIdEntity(Long administrationId) {
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