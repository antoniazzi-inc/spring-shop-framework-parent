package com.github.antoniazzi.inc.backend.commons.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.github.antoniazzi.inc.backend.commons.eh.EntityNotFoundException;
import com.github.antoniazzi.inc.backend.commons.entity.BaseEntity;
import com.github.antoniazzi.inc.backend.commons.repository.BaseRepository;
import com.github.antoniazzi.inc.backend.commons.service.EntityService;

/**
 * Entity Service Class to be extended by all Entity Services
 * 
 * @version 0.2.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 *
 * @param <E>  The Entity Type
 * @param <ID> The ID Type
 * @param <R>  The Repository Type
 */
@Transactional
public abstract class EntityServiceImpl<E extends BaseEntity<ID>, ID extends Serializable, R extends BaseRepository<E, ID>> extends BaseServiceImpl
		implements EntityService<E, ID> {

	protected final R repository;

	public EntityServiceImpl(R repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public E create(E obj) {
		return repository.save(obj);
	}

	@Override
	@Transactional(readOnly = true)
	public E get(ID id) {
		return getOrThrow(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> getAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> getAll(Specification<E> specification) {
		return repository.findAll(specification);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> getAll(Specification<E> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	@Transactional
	public E update(E obj) {
		return update(obj, BaseEntity.getNullPropertyNames(obj));
	}

	@Transactional
	protected E update(E obj, String[] ignoreProperties) {
		E e = getOrThrow(obj.getId());

		BeanUtils.copyProperties(obj, e, ignoreProperties);

		return repository.save(e);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		this.delete(getOrThrow(id));
	}

	@Transactional
	protected void delete(E e) {
		repository.delete(e);
	}

	@Transactional
	protected E getOrThrow(ID id) {
		Optional<E> oE = repository.findOneById(id);

		if (!oE.isPresent()) {
			throw new EntityNotFoundException(id.toString());
		}

		return oE.get();
	}

}
