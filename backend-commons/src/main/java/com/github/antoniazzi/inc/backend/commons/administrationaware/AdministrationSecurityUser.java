package com.github.antoniazzi.inc.backend.commons.administrationaware;

import com.github.antoniazzi.inc.backend.commons.administrationaware.entity.AdministrationAwareEntity;

/**
 * Administration Aware Security User to be implemented by a Custom User Entity
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public interface AdministrationSecurityUser extends SecurityUser, AdministrationAwareEntity {

}
