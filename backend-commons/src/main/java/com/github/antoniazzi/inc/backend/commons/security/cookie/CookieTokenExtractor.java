package com.github.antoniazzi.inc.backend.commons.security.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.antoniazzi.inc.backend.commons.security.token.TokenProvider;

/**
 * Extracts the access token from a cookie. Falls back to a
 * <code>BearerTokenExtractor</code> extracting information from the
 * Authorization header, if no cookie was found.
 */
public class CookieTokenExtractor {

	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * Extract the JWT access token from the request, if present. If not, then it
	 * falls back to the {@link BearerTokenExtractor} behaviour.
	 *
	 * @param request the request containing the cookies.
	 * @return the extracted JWT token; or null.
	 */
	protected String extractToken(HttpServletRequest request) {
		String result;
		Cookie accessTokenCookie = CookieService.getAccessTokenCookie(request);

		if (accessTokenCookie != null) {
			result = accessTokenCookie.getValue();
		} else {
			result = tokenProvider.resolveToken(request);
		}

		return result;
	}

}
