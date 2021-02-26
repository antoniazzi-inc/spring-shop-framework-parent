package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

/**
 * Base Entity Class to be extended by all Administration Version Aware Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationVIdEntity extends BaseVIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = -6717234150605059442L;

	@Column(name = "administration_id")
	protected Long administrationId;

	public BaseAdministrationVIdEntity() {

	}

	public BaseAdministrationVIdEntity(Long administrationId) {
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