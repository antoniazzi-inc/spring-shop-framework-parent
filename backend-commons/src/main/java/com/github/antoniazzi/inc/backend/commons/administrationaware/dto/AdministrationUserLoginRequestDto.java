package com.github.antoniazzi.inc.backend.commons.administrationaware.dto;

/**
 * Dto used by the {@link AdministrationAwareAuthenticationController}
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public class AdministrationUserLoginRequestDto extends UserLoginRequestDto {

	private static final long serialVersionUID = -5227310008107028912L;

	private String administrationKey;

	public AdministrationUserLoginRequestDto() {

	}

	public AdministrationUserLoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AdministrationUserLoginRequestDto(String username, String password, String administrationKey) {
		this.username = username;
		this.password = password;
		this.administrationKey = administrationKey;
	}

	public AdministrationUserLoginRequestDto(String username, String password, String administrationKey,
			Boolean rememberMe) {
		this.username = username;
		this.password = password;
		this.administrationKey = administrationKey;
		this.rememberMe = rememberMe;
	}

	public String getAdministrationKey() {
		return administrationKey;
	}

	public void setAdministrationKey(String administrationKey) {
		this.administrationKey = administrationKey;
	}

}
