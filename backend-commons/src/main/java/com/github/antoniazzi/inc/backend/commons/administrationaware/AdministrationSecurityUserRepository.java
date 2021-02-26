package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.util.Optional;

/**
 * Administration Aware Security User Repository to be implemented by a Custom
 * User Repository
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public interface AdministrationSecurityUserRepository {

	Optional<AdministrationSecurityUser> findByUsernameAndAdministrationId(String username, Long administrationId);

	Optional<AdministrationSecurityUser> findByIdAndAdministrationIdWithAuthorities(Long id, Long administrationId);

}
