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
public class BaseVMapsIdEntity extends BaseMapsIdEntity implements VersionAwareEntity {

	private static final long serialVersionUID = 7680934214985867539L;

	@Version
	protected Long version;

	public BaseVMapsIdEntity() {

	}

	public BaseVMapsIdEntity(Long id) {
		this.id = id;
	}

	public BaseVMapsIdEntity(Long id, Long version) {
		this.id = id;
		this.version = version;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}