package com.github.antoniazzi.inc.backend.commons.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * Base Entity Class to be extended by all String Type ID Entities
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseStringIdEntity extends BaseEntity<String> implements StringIdAwareEntity {

	private static final long serialVersionUID = -9146717921360278026L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
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