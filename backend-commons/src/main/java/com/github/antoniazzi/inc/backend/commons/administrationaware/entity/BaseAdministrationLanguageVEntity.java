package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseLanguageVEntity;

/**
 * Base Entity Class to be extended by all Administration Language Aware
 * Entities
 * 
 * @version 1.0.0
 * @since 14.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationLanguageVEntity extends BaseLanguageVEntity
		implements AdministrationLanguageAwareEntity {

	private static final long serialVersionUID = -7480213016493600749L;

	@Column(name = "administration_id")
	protected Long administrationId;

	public BaseAdministrationLanguageVEntity() {

	}

	public BaseAdministrationLanguageVEntity(Long administrationId) {
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