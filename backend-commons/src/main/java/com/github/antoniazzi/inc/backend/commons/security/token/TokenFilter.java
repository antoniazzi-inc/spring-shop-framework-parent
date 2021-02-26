package com.github.antoniazzi.inc.backend.commons.security.token;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.github.antoniazzi.inc.backend.commons.security.cookie.CookieAuthenticationService;
import com.github.antoniazzi.inc.backend.commons.security.cookie.CookieService;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Filters and Resolves the JTW Token
 * 
 * @version 1.0.0
 * @since 29.09.2019
 * @author Kristijan Georgiev
 */
public class TokenFilter extends GenericFilterBean {

	private static final int REFRESH_WINDOW_SECS = 30;

	private TokenProvider tokenProvider;

	private Map<String, List<String>> ignoredRoutes;

	private CookieAuthenticationService<?> cookieAuthenticationService;

	public TokenFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	public TokenFilter(TokenProvider tokenProvider, CookieAuthenticationService<?> authenticationService,
			Map<String, List<String>> ignoredRoutes) {
		this.tokenProvider = tokenProvider;
		this.cookieAuthenticationService = authenticationService;
		this.ignoredRoutes = ignoredRoutes;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		String jwt = null;

		Cookie accessTokenCookie = CookieService.getAccessTokenCookie(httpServletRequest);

		if (accessTokenCookie != null && StringUtils.hasText(accessTokenCookie.getValue())) {
			jwt = accessTokenCookie.getValue();
		} else {
			jwt = tokenProvider.resolveToken(httpServletRequest);
		}

		if (cookieAuthenticationService != null
				&& !isInIgnoredRoutes(httpServletRequest.getRequestURI(), httpServletRequest.getMethod())) {
			try {
				httpServletRequest = refreshTokensIfExpiring(jwt, httpServletRequest, httpServletResponse);
			} catch (Exception e) {
				httpServletRequest = cookieAuthenticationService.stripTokens(httpServletRequest);
			}
		}

		Authentication authentication = null;

		if (tokenProvider.validateToken(jwt)) {
			authentication = this.tokenProvider.getAuthentication(jwt);
		}

		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	public HttpServletRequest refreshTokensIfExpiring(String jwt, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		HttpServletRequest newHttpServletRequest = httpServletRequest;

		if (mustRefreshToken(jwt)) {
			Cookie refreshCookie = CookieService.getRefreshTokenCookie(httpServletRequest);
			if (refreshCookie != null) {
				try {
					newHttpServletRequest = cookieAuthenticationService.refreshToken(httpServletRequest,
							httpServletResponse, refreshCookie);
				} catch (Exception e) {
					throw new AccessDeniedException("Can not refresh token");
				}
			}
		}

		return newHttpServletRequest;
	}

	private boolean mustRefreshToken(String jwt) {
		if (jwt == null) {
			return false;
		}

		Integer expSeconds = null;

		try {
			expSeconds = (Integer) AbstractTokenProvider.getClaim(jwt, "exp");
		} catch (ExpiredJwtException e) {
			return true;
		}

		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

		Integer expiresIn = (int) (expSeconds - now.toEpochSecond());

		if (expiresIn < REFRESH_WINDOW_SECS) {
			return true;
		}

		return false;
	}

	private boolean isInIgnoredRoutes(String url, String method) {
		if (ignoredRoutes == null) {
			return false;
		}

		if (ignoredRoutes.containsKey(url) && ignoredRoutes.get(url).contains(method)) {
			return true;
		}

		return false;
	}
}
