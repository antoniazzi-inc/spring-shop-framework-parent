package com.github.antoniazzi.inc.backend.commons.security.token;

import io.jsonwebtoken.JwtBuilder;

/**
 * Transfer JWT Token Builders
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public class TokensTransfer {

	private JwtBuilder accessTokenBuilder;

	private JwtBuilder refreshTokenBuilder;

	public TokensTransfer() {

	}

	public TokensTransfer(JwtBuilder accessTokenBuilder) {
		this.accessTokenBuilder = accessTokenBuilder;
	}

	public TokensTransfer(JwtBuilder accessTokenBuilder, JwtBuilder refreshTokenBuilder) {
		this.accessTokenBuilder = accessTokenBuilder;
		this.refreshTokenBuilder = refreshTokenBuilder;
	}

	public JwtBuilder getAccessTokenBuilder() {
		return accessTokenBuilder;
	}

	public void setAccessTokenBuilder(JwtBuilder accessTokenBuilder) {
		this.accessTokenBuilder = accessTokenBuilder;
	}

	public JwtBuilder getRefreshTokenBuilder() {
		return refreshTokenBuilder;
	}

	public void setRefreshTokenBuilder(JwtBuilder refreshTokenBuilder) {
		this.refreshTokenBuilder = refreshTokenBuilder;
	}

}
