package com.github.antoniazzi.inc.backend.commons.administrationaware.dto;

import javax.validation.constraints.NotEmpty;

import com.github.antoniazzi.inc.backend.commons.model.dto.request.BaseRequestDto;

/**
 * DTO used by the {@link UserAwareAuthenticationController}
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public class UserLoginRequestDto extends BaseRequestDto {

	private static final long serialVersionUID = -1740850785891990774L;

	@NotEmpty
	protected String username;

	@NotEmpty
	protected String password;

	protected Boolean rememberMe = false;

	public UserLoginRequestDto() {

	}

	public UserLoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserLoginRequestDto(String username, String password, Boolean rememberMe) {
		this.username = username;
		this.password = password;
		this.rememberMe = rememberMe;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "LoginRequestDto [username=" + username + ", password=" + password + ", rememberMe=" + rememberMe + "]";
	}

}
