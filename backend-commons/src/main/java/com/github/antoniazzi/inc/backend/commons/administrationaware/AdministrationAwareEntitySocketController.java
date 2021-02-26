package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.antoniazzi.inc.backend.commons.entity.BaseEntity;
import com.github.antoniazzi.inc.backend.commons.service.EntityService;
import com.github.antoniazzi.inc.backend.commons.socket.SocketAction;
import com.github.antoniazzi.inc.backend.commons.socket.SocketWrapperResponseDto;
import com.github.antoniazzi.inc.backend.commons.web.rest.EntitySocketController;

/**
 * Base Entity Socket Controller Class to be extended by all Entity Controllers with socket support
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 *
 * @param <E>  The Entity Type
 * @param <ID> The ID Type
 * @param <S>  The Service Type
 */
public abstract class AdministrationAwareEntitySocketController<E extends BaseEntity<ID>, ID extends Serializable, S extends EntityService<E, ID>>
		extends EntitySocketController<E, ID, S> {

	@Autowired
	private AdministrationAwareSecurityService securityService;

	public AdministrationAwareEntitySocketController(S service) {
		super(service);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void preSend(Object obj, SocketAction action) {
		if (!securityService.getUserAuthorities().contains("ROLE_SUPER_ADMIN")) {
			Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

			send(securityService.getAuthentication().getAdministrationUid(), new SocketWrapperResponseDto(obj, action, clazz.getSimpleName()));
		}
	}

}
