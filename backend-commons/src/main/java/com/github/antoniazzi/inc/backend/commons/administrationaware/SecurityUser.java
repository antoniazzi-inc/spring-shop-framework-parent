package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.github.antoniazzi.inc.backend.commons.entity.LongIdAwareEntity;

/**
 * Security User to be implemented by a Custom User Entity
 * 
 * @version 1.0.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
public interface SecurityUser extends LongIdAwareEntity {

	public String getUid();

	public String getUsername();

	public String getPassword();

	public Boolean getEnabled();

	public String getLanguageKey();

	public Collection<? extends GrantedAuthority> getAuthorities();

}
