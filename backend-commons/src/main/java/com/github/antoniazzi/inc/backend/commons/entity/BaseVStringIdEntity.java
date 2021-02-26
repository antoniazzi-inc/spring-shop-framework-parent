package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base Entity Class to be extended by all Version Aware String Type ID Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseVStringIdEntity extends BaseStringIdEntity implements VersionAwareEntity {

	private static final long serialVersionUID = 7262902650755225380L;

	@Version
	protected Long version;

	public BaseVStringIdEntity() {

	}

	public BaseVStringIdEntity(String id) {
		this.id = id;
	}

	public BaseVStringIdEntity(String id, Long version) {
		this.id = id;
		this.version = version;
	}

	public BaseVStringIdEntity(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}