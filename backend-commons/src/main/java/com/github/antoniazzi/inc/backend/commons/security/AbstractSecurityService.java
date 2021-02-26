package com.github.antoniazzi.inc.backend.commons.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Abstract Security Service for fetching and checking Authentication object
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public abstract class AbstractSecurityService<T extends Authentication> implements SecurityService {

	@SuppressWarnings("unchecked")
	public T getAuthentication() {
		try {
			return (T) SecurityContextHolder.getContext().getAuthentication();
		} catch (Exception e) {
			return null;
		}
	}

	public Set<String> getUserAuthorities() {
		Set<String> authorities = new HashSet<>();

		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(authority -> {
			authorities.add(authority.getAuthority());
		});

		return authorities;
	}

	public boolean userHasAuthority(String authority) {
		if (getUserAuthorities().contains(authority)) {
			return true;
		}

		return false;
	}

	public boolean userHasAuthorities(Set<String> authorities) {
		if (getUserAuthorities().containsAll(authorities)) {
			return true;
		}

		return false;
	}

	public Authentication getAuthenticationOrThrow() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			throw new AccessDeniedException("Access Denied");
		}

		return authentication;
	}

}
