package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import com.github.antoniazzi.inc.backend.commons.entity.IdAwareEntity;

/**
 * Interface to be implemented by all User Aware Entities
 * 
 * @version 1.0.0
 * @since 13.11.2019
 * @author Kristijan Georgiev
 */
public interface UserAwareEntity extends IdAwareEntity {

	public Long getUserId();

	public void setUserId(Long userId);

}
