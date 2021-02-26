package com.github.antoniazzi.inc.backend.commons.security.token;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;

/**
 * Token Provider Interface
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
public interface TokenProvider extends InitializingBean {

	public static final String AUTHORITIES_KEY = "authorities";

	public static final String ATI_KEY = "ati";

	public static final String TYP_KEY = "typ";
	public static final String TYP_VALUE = "JWT";

	public static final String TOKEN_TYPE_KEY = "token_type";
	public static final String TOKEN_TYPE_VALUE = "bearer";

	public static final String AUTHORIZATION_HEADER_KEY = "Authorization";

	public static final String AUTHORIZATION_TOKEN_KEY = "access_token";
	public static final String AUTHORIZATION_TOKEN_REFRESH_KEY = "refresh_token";
	public static final String AUTHORIZATION_TOKEN_SESSION_KEY = "session_token";

	public TokenResponseDto createToken(Authentication authentication);

	public Authentication getAuthentication(String token);

	public boolean validateToken(String accessToken);

	public boolean validateToken(String token, boolean isAccessToken);

	public String resolveToken(Object request);

}
