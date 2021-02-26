package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;
import com.github.antoniazzi.inc.backend.commons.entity.LongIdAwareEntity;

/**
 * Security Administration Aware Repository to be implemented by an
 * Administration Repository
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public interface SecurityAdministrationRepository<ID> {

	Optional<LongIdAwareEntity> findByAdministrationKey(String administrationKey);

	abstract Optional<? extends BaseVIdEntity> findOneById(@Param("id") ID id);

}
