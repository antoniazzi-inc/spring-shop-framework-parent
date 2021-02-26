package com.github.antoniazzi.inc.backend.commons.administrationaware.dto;

/**
 * Dto used by the {@link AdministrationAwareAuthenticationController}
 * 
 * @version 1.0.0
 * @since 14.01.2020
 * @author Kristijan Georgiev
 */
public class AdministrationIdUserLoginRequestDto extends UserLoginRequestDto {

	private static final long serialVersionUID = -5227310008107028912L;

	private Long administrationId;
	private String administrationUid;

	public AdministrationIdUserLoginRequestDto() {

	}

	public AdministrationIdUserLoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AdministrationIdUserLoginRequestDto(String username, String password, Long administrationId) {
		this.username = username;
		this.password = password;
		this.administrationId = administrationId;
	}

	public AdministrationIdUserLoginRequestDto(String username, String password, Long administrationId,
			String administrationUid) {
		this.username = username;
		this.password = password;
		this.administrationId = administrationId;
		this.administrationUid = administrationUid;
	}

	public AdministrationIdUserLoginRequestDto(String username, String password, Long administrationId,
			Boolean rememberMe) {
		this.username = username;
		this.password = password;
		this.administrationId = administrationId;
		this.rememberMe = rememberMe;
	}

	public AdministrationIdUserLoginRequestDto(String username, String password, Long administrationId,
			String administrationUid, Boolean rememberMe) {
		this.username = username;
		this.password = password;
		this.administrationId = administrationId;
		this.administrationUid = administrationUid;
		this.rememberMe = rememberMe;
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

}
