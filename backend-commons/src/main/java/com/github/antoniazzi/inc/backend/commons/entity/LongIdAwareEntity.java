package com.github.antoniazzi.inc.backend.commons.entity;

/**
 * Interface to be implemented by all Long Id Aware Entities
 * 
 * @version 1.0.0
 * @since 13.11.2019
 * @author Kristijan Georgiev
 */
public interface LongIdAwareEntity extends IdAwareEntity {

	public Long getId();

	public void setId(Long id);

}
