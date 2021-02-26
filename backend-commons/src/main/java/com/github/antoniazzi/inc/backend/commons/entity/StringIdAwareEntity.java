package com.github.antoniazzi.inc.backend.commons.entity;

/**
 * Interface to be implemented by all String Id Aware Entities
 * 
 * @version 1.0.0
 * @since 13.11.2019
 * @author Kristijan Georgiev
 */
public interface StringIdAwareEntity extends IdAwareEntity {

	public String getId();

	public void setId(String id);

}
