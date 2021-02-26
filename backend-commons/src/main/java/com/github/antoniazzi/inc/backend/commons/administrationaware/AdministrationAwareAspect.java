package com.github.antoniazzi.inc.backend.commons.administrationaware;

import javax.persistence.EntityManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Gets @AdministrationAwareService and applies hibernate administration aware
 * filters
 * 
 * @version 1.0.0
 * @since 09.11.2019
 * @author Kristijan Georgiev
 */
@Aspect
@Component
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ADMINISTRATION_AWARE_ASPECT, havingValue = "true")
public class AdministrationAwareAspect {

	private static final Logger log = LoggerFactory.getLogger(AdministrationAwareAspect.class);

	{
		log.info("Registering Administration Aware Aspect");
	}

	@Autowired
	private ApplicationContext context;

	@Autowired
	private AdministrationAwareSecurityService securityService;

	@Pointcut("target(nl.wpsoft.backend.commons.service.EntityService)")
	public void entityServicePointcut() {

	}

	@Around("entityServicePointcut()")
	public Object aroundAdministrationAwareService(ProceedingJoinPoint pjp) throws Throwable {
		// check if service is administration aware
		if (pjp.getTarget().getClass().isAnnotationPresent(AdministrationAwareService.class)) {
			if (securityService.getAuthentication() != null
					&& securityService.getAuthentication() instanceof AdministrationUserAuthentication
					&& securityService.getAuthentication().getAdministrationId() != null) {
				EntityManager entityManager = context.getBean(EntityManager.class);

				Filter filter = entityManager.unwrap(Session.class).enableFilter("ADMINISTRATION_FILTER");
				filter.setParameter("administrationId", securityService.getAuthentication().getAdministrationId());
			}

		}

		return pjp.proceed();
	}

}
