package com.github.antoniazzi.inc.backend.commons.security;

import java.util.Set;

import org.springframework.security.core.Authentication;

public interface SecurityService {

	public Set<String> getUserAuthorities();

	public boolean userHasAuthority(String authority);

	public boolean userHasAuthorities(Set<String> authorities);

	public abstract Authentication getAuthentication();

	public Authentication getAuthenticationOrThrow();

}
