package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base Entity Class to be extended by all @MapsId Type ID Entities
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseMapsIdEntity extends BaseEntity<Long> implements LongIdAwareEntity {

	private static final long serialVersionUID = 5478124673737616325L;

	@Id
	protected Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}