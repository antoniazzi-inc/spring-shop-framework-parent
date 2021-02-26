package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.github.antoniazzi.inc.backend.commons.administrationaware.entity.AdministrationAwareEntity;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;

/**
 * Entity listener to be used on administration aware entities
 * 
 * @version 1.0.0
 * @since 09.11.2019
 * @author Kristijan Georgiev
 */
@Transactional
public class AdministrationAwareEntityListener {

	private static final String GET_VERSION = "getVersion";

	@Autowired
	private ApplicationContext context;

	@Autowired
	private AdministrationAwareSecurityService securityService;

	@PrePersist
	public void prePersist(AdministrationAwareEntity dto) {
		if (securityService.getAuthentication() != null && securityService.getAuthentication().getAdministrationId() != null) {
			// System.err.println(securityService.getAuthentication().getAdministrationId());
			dto.setAdministrationId(securityService.getAuthentication().getAdministrationId());
		}
	}

	@PreUpdate
	public void preUpdate(AdministrationAwareEntity dto) {
		if (securityService.getAuthentication() != null && securityService.getAuthentication().getAdministrationId() != null) {
			if (dto.getAdministrationId() == null)
				dto.setAdministrationId(securityService.getAuthentication().getAdministrationId());
			// AdministrationAwareEntity tmp;
			/*
			 * try { tmp = dto.getClass().getDeclaredConstructor().newInstance(); } catch (Exception e) { throw new
			 * NoSuchElementException(); }
			 * 
			 * BeanUtils.copyProperties(dto, tmp);
			 */
			if (dto.getAdministrationId() != null && !securityService.getAuthentication().getAdministrationId().equals(dto.getAdministrationId()))
				throw new NoSuchElementException();

			EntityManager entityManager = context.getBean(EntityManager.class);
			// entityManager.refresh(dto);

			AdministrationAwareEntity entity = entityManager.find(dto.getClass(), dto.getId());

			if (!securityService.getAuthentication().getAdministrationId().equals(entity.getAdministrationId()))
				throw new NoSuchElementException();

			// checks if versions are the same
			try {
				Class<?> clazz = entity.getClass();
				Method getVersion = clazz.getMethod(GET_VERSION);

				Long entityVersion = (Long) getVersion.invoke(entity);
				Long dtoVersion = (Long) getVersion.invoke(dto);

				if (!entityVersion.equals(dtoVersion)) {
					throw new BadRequestException();
				}
			} catch (BadRequestException e) {
				throw new BadRequestException();
			} catch (Exception e) {
			}

			// BeanUtils.copyProperties(tmp, dto);
		}
	}

	@PreRemove
	public void preRemove(AdministrationAwareEntity dto) {
		if (securityService.getAuthentication() != null && securityService.getAuthentication().getAdministrationId() != null) {
			if (!securityService.getAuthentication().getAdministrationId().equals(dto.getAdministrationId()))
				throw new NoSuchElementException();
		}

	}

}
