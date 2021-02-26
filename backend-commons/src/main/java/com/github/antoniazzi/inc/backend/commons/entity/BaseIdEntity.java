package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base Entity Class to be extended by all Long Type ID Entities
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseIdEntity extends BaseEntity<Long> implements LongIdAwareEntity {

	private static final long serialVersionUID = -2345834952919214001L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	public BaseIdEntity() {

	}

	public BaseIdEntity(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}