package com.github.antoniazzi.inc.backend.commons.security.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.response.BaseResponseDto;

/**
 * Response DTO to get when authenticating
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
@JsonInclude(value = Include.NON_EMPTY)
public class TokenResponseDto extends BaseResponseDto {

	private static final long serialVersionUID = -102329936932691663L;

	private Long accessTokenExpiresIn;

	private String tokenType = TokenProvider.TOKEN_TYPE_VALUE;

	private String accessToken;

	private Long refreshTokenExpiresIn;

	private String refreshToken;

	public TokenResponseDto() {

	}

	public TokenResponseDto(Long accessTokenExpiresIn, String accessToken) {
		this.accessTokenExpiresIn = accessTokenExpiresIn;
		this.accessToken = accessToken;
	}

	public TokenResponseDto(Long accessTokenExpiresIn, String accessToken, String refreshToken,
			Long refreshTokenExpiresIn) {
		this.accessTokenExpiresIn = accessTokenExpiresIn;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public TokenResponseDto(String tokenType, Long accessTokenExpiresIn, String accessToken, String refreshToken,
			Long refreshTokenExpiresIn) {
		this.tokenType = tokenType;
		this.accessTokenExpiresIn = accessTokenExpiresIn;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public Long getAccessTokenExpiresIn() {
		return accessTokenExpiresIn;
	}

	public void setAccessTokenExpiresIn(Long accessTokenExpiresIn) {
		this.accessTokenExpiresIn = accessTokenExpiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}

	public void setRefreshTokenExpiresIn(Long refreshTokenExpiresIn) {
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
