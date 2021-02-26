package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base Entity Class to be extended by all Version Aware @MapsId Type ID
 * Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseVMapsStringIdEntity extends BaseMapsStringIdEntity implements VersionAwareEntity {

	private static final long serialVersionUID = 4386960475546154952L;

	@Version
	protected Long version;

	public BaseVMapsStringIdEntity() {

	}

	public BaseVMapsStringIdEntity(String id) {
		this.id = id;
	}

	public BaseVMapsStringIdEntity(String id, Long version) {
		this.id = id;
		this.version = version;
	}

	public BaseVMapsStringIdEntity(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}