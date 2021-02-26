package com.github.antoniazzi.inc.backend.commons.administrationaware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;
import com.github.antoniazzi.inc.backend.commons.security.AbstractSecurityService;

/**
 * Administration Aware Security Service for fetching and checking
 * Authentication object
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
@Service
@ConditionalOnClass(SecurityContextHolder.class)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ADMINISTRATION_AWARE_SECURITY_SERVICE, havingValue = "true")
public class AdministrationAwareSecurityService extends AbstractSecurityService<AdministrationUserAuthentication> {

	private static final Logger log = LoggerFactory.getLogger(AdministrationAwareSecurityService.class);

	{
		log.info("Registering Administration Aware Security Service");
	}

}
