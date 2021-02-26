package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base Entity Class to be extended by all @MapsId Type ID Entities
 * 
 * @version 1.0.0
 * @since 13.11.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseMapsStringIdEntity extends BaseEntity<String> implements StringIdAwareEntity {

	private static final long serialVersionUID = -9042459273173447110L;

	@Id
	protected String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}