package com.github.antoniazzi.inc.backend.commons.entity;

import java.io.Serializable;

/**
 * Interface to be implemented by all Id Aware Entities
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public interface IdAwareEntity {

	public Serializable getId();

}
