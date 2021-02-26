package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

import com.github.antoniazzi.inc.backend.commons.entity.IdAwareEntity;

/**
 * Interface to be implemented by all Administration Aware Entities
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */

public interface AdministrationAwareEntity extends IdAwareEntity {

	public Long getAdministrationId();

	public void setAdministrationId(Long administrationId);

}
