package com.github.antoniazzi.inc.backend.commons.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.github.antoniazzi.inc.backend.commons.entity.BaseEntity;

/**
 * Entity Service Interface to be extended by all Entity Service Interfaces
 * 
 * @version 0.1.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 *
 * @param <E>  The Entity Type
 * @param <ID> The ID Type
 */
public interface EntityService<E extends BaseEntity<ID>, ID extends Serializable> extends BaseService {

	E create(E obj);

	E get(ID id);

	Page<E> getAll(Pageable pageable);

	List<E> getAll(Specification<E> specification);

	Page<E> getAll(Specification<E> specification, Pageable pageable);

	E update(E obj);

	void delete(ID id);

}
