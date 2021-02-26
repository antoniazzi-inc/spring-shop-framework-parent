package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseMapsStringIdEntity;

/**
 * Base Entity Class to be extended by all Administration Aware @MapsId Type ID
 * Entities
 * 
 * @version 1.0.0
 * @since 14.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseAdministrationMapsStringIdEntity extends BaseMapsStringIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = 9126933715501001334L;

	@Column(name = "administration_id")
	protected Long administrationId;

	public BaseAdministrationMapsStringIdEntity() {

	}

	public BaseAdministrationMapsStringIdEntity(Long administrationId) {
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