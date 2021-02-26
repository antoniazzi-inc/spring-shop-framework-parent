package com.github.antoniazzi.inc.backend.commons.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Repository Interface to be extended by all Repository Interfaces
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 *
 * @param <T>  The Entity Type
 * @param <ID> The ID Type
 */
@Transactional
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	@Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
	Optional<T> findOneById(@Param("id") ID id);

}
