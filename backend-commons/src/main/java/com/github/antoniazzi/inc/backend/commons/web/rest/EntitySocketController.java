package com.github.antoniazzi.inc.backend.commons.web.rest;

import java.io.Serializable;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.antoniazzi.inc.backend.commons.entity.BaseEntity;
import com.github.antoniazzi.inc.backend.commons.service.EntityService;
import com.github.antoniazzi.inc.backend.commons.socket.SocketAction;
import com.github.antoniazzi.inc.backend.commons.socket.SocketWrapperResponseDto;

/**
 * Base Entity Socket Controller Class to be extended by all Entity Controllers
 * with socket support
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 *
 * @param <E>  The Entity Type
 * @param <ID> The ID Type
 * @param <S>  The Service Type
 */
public abstract class EntitySocketController<E extends BaseEntity<ID>, ID extends Serializable, S extends EntityService<E, ID>>
		extends EntityController<E, ID, S> {

	public static final String BASE_URL = "/session";

	@Autowired
	protected SimpMessagingTemplate webSocket;

	public EntitySocketController(S service) {
		super(service);
	}

	@Override
	@PostMapping
	public ResponseEntity<E> create(@RequestBody @Valid E dto) throws URISyntaxException {
		ResponseEntity<E> response = super.create(dto);

		preSend(response.getBody(), SocketAction.CREATE);

		return response;
	}

	@Override
	@PutMapping
	public ResponseEntity<E> update(@RequestBody @Valid E dto) {
		ResponseEntity<E> response = super.update(dto);

		preSend(response.getBody(), SocketAction.UPDATE);

		return response;
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		super.delete(id);

		preSend(id, SocketAction.DELETE);

		return ResponseEntity.noContent().build();
	}

	protected abstract void preSend(Object obj, SocketAction action);

	protected void send(String url, SocketWrapperResponseDto dto) {
		String fullUrl = BASE_URL;

		if (url.startsWith("/")) {
			fullUrl += url;
		} else {
			fullUrl = fullUrl + "/" + url;
		}

		webSocket.convertAndSend(fullUrl, dto);
	}

}
