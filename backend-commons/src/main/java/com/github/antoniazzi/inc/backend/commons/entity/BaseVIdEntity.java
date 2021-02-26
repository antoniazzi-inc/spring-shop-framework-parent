package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base Entity Class to be extended by all Version Aware Long Type ID Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseVIdEntity extends BaseIdEntity implements VersionAwareEntity {

	private static final long serialVersionUID = 7655972900321910360L;

	@Version
	protected Long version;

	public BaseVIdEntity() {

	}

	public BaseVIdEntity(Long id) {
		this.id = id;
	}

	public BaseVIdEntity(Long id, Long version) {
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