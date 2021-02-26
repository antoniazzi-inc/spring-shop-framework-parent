package com.github.antoniazzi.inc.backend.commons.entity;

/**
 * Interface to be implemented by all Version Aware Entities
 * 
 * @version 1.0.0
 * @since 13.01.2020
 * @author Kristijan Georgiev
 */
public interface VersionAwareEntity {

	public Long getVersion();

	public void setVersion(Long version);

}
