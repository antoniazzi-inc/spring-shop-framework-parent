package com.github.antoniazzi.inc.backend.commons.web.rest;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.antoniazzi.inc.backend.commons.dq.DynamicVisitor;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.eh.ExceptionConst;
import com.github.antoniazzi.inc.backend.commons.entity.BaseEntity;
import com.github.antoniazzi.inc.backend.commons.service.EntityService;

import cz.jirutka.rsql.parser.RSQLParser;

/**
 * Base Entity Controller Class to be extended by all Entity Controllers
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 *
 * @param <E>  The Entity Type
 * @param <ID> The ID Type
 * @param <S>  The Service Type
 */
public abstract class EntityController<E extends BaseEntity<ID>, ID extends Serializable, S extends EntityService<E, ID>>
		extends BaseController {

	@Autowired
	private ApplicationContext context;

	protected final S service;

	public EntityController(S service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<E> create(@RequestBody @Valid E dto) throws URISyntaxException {
		if (dto.getId() != null) {
			throw new BadRequestException(ExceptionConst.ID_MUST_BE_NULL);
		}

		E entity = service.create(dto);

		return ResponseEntity.created(new URI("entity:" + dto.getClass().getSimpleName() + "/" + entity.getId()))
				.body(entity);
	}

	@GetMapping("/{id}")
	public ResponseEntity<E> get(@PathVariable ID id) {
		return ResponseEntity.ok().body(service.get(id));
	}

	@GetMapping
	public ResponseEntity<Page<E>> getAll(@RequestParam(required = false) String q, Pageable pageable) {
		return ResponseEntity.ok().body(service.getAll(queryToSpecification(q), pageable));
	}

	@PutMapping
	public ResponseEntity<E> update(@RequestBody @Valid E dto) {
		if (dto.getId() == null) {
			throw new BadRequestException(ExceptionConst.ID_MUST_NOT_BE_NULL);
		}

		return ResponseEntity.ok().body(service.update(dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	protected Specification<E> queryToSpecification(String search) {
		if (search == null) {
			return null;
		}

		return ((RSQLParser) context.getBean(RSQLParser.class)).parse(search).accept(new DynamicVisitor<E>());
	}

}
