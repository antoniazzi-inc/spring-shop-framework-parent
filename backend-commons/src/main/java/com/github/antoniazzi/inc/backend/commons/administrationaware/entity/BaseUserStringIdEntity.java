package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import javax.persistence.MappedSuperclass;

import com.github.antoniazzi.inc.backend.commons.entity.BaseStringIdEntity;

/**
 * Base Entity Class to be extended by all User Aware Entities
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseUserStringIdEntity extends BaseStringIdEntity implements UserAwareEntity {

	private static final long serialVersionUID = -3025404949209070201L;

	protected Long userId;

	public BaseUserStringIdEntity() {

	}

	public BaseUserStringIdEntity(Long userId) {
		this.userId = userId;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}