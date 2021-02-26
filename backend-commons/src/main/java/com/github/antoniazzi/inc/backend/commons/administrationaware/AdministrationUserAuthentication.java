package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Administration Security User Authentication class
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public class AdministrationUserAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private boolean fromToken = false;
	private boolean rememberMe = false;

	private Long userId;
	private String userUid;

	private Object principal;
	private Object credentials;

	private String languageKey;

	private Map<String, Object> additionalData = new HashMap<>();

	private Long administrationId;
	private String administrationUid;
	private String administrationKey;

	public AdministrationUserAuthentication() {
		super(null);
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	public AdministrationUserAuthentication(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
	}

	public AdministrationUserAuthentication(Object principal, Object credentials, boolean rememberMe) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.rememberMe = rememberMe;
	}

	public AdministrationUserAuthentication(Object principal, Object credentials, String administrationKey) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationKey = administrationKey;
	}

	public AdministrationUserAuthentication(Object principal, Object credentials, String administrationKey,
			boolean rememberMe) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationKey = administrationKey;
		this.rememberMe = rememberMe;
	}

	public AdministrationUserAuthentication(Object principal, Object credentials, Long administrationId) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationId = administrationId;
	}

	public AdministrationUserAuthentication(Object principal, Object credentials, Long administrationId,
			boolean rememberMe) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationId = administrationId;
		this.rememberMe = rememberMe;
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials, boolean rememberMe) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.rememberMe = rememberMe;
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials, String administrationKey) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationKey = administrationKey;
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials, String administrationKey, boolean rememberMe) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationKey = administrationKey;
		this.rememberMe = rememberMe;
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials, Long administrationId) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationId = administrationId;
	}

	public AdministrationUserAuthentication(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials, Long administrationId, boolean rememberMe) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.administrationId = administrationId;
		this.rememberMe = rememberMe;
	}

	public boolean isFromToken() {
		return fromToken;
	}

	public void setFromToken(boolean fromToken) {
		this.fromToken = fromToken;
	}

	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	public String getLanguageKey() {
		return languageKey;
	}

	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}

	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Map<String, Object> additionalData) {
		this.additionalData = additionalData;
	}

	public Long getAdministrationId() {
		return administrationId;
	}

	public void setAdministrationId(Long administrationId) {
		this.administrationId = administrationId;
	}

	public String getAdministrationUid() {
		return administrationUid;
	}

	public void setAdministrationUid(String administrationUid) {
		this.administrationUid = administrationUid;
	}

	public String getAdministrationKey() {
		return administrationKey;
	}

	public void setAdministrationKey(String administrationKey) {
		this.administrationKey = administrationKey;
	}

}
