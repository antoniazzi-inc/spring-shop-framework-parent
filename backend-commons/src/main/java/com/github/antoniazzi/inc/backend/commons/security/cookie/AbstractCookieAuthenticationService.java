package com.github.antoniazzi.inc.backend.commons.security.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.github.antoniazzi.inc.backend.commons.model.dto.request.BaseRequestDto;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenResponseDto;

public abstract class AbstractCookieAuthenticationService<T extends BaseRequestDto> implements CookieAuthenticationService<T> {

	/**
	 * Number of milliseconds to cache refresh token grants so we don't have to repeat them in case of parallel requests.
	 */
	protected static final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 5000l;

	protected final CookiesPersistentTokenCache<TokenCookies> recentlyRefreshed;

	@Autowired
	protected CookieService cookieService;

	public AbstractCookieAuthenticationService() {
		this.recentlyRefreshed = new CookiesPersistentTokenCache<>(REFRESH_TOKEN_VALIDITY_MILLISECONDS);
	}

	@Override
	public ResponseEntity<? extends TokenResponseDto> returnTokenResponseWithSecurityCookies(ResponseEntity<TokenResponseDto> tokens, boolean rememberMe,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		setSecurityCookies(tokens, new TokenCookies(), rememberMe, httpServletRequest, httpServletResponse);

		return tokens;
	}

	@Override
	public void setSecurityCookies(ResponseEntity<TokenResponseDto> tokens, TokenCookies cookies, boolean rememberMe, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		cookieService.createCookies(httpServletRequest, tokens.getBody(), rememberMe, cookies);

		cookies.addCookiesTo(httpServletResponse);
	}

	@Override
	public HttpServletRequest refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Cookie refreshCookie) {

		if (cookieService.isSessionExpired(refreshCookie)) {
			logout(httpServletRequest, httpServletResponse);

			return stripTokens(httpServletRequest);
		}

		TokenCookies cookies = getCachedCookies(refreshCookie.getValue());

		synchronized (cookies) {
			// check if we have a result from another thread already
			if (cookies.getAccessTokenCookie() == null) {
				String refreshCookieValue = CookieService.getRefreshTokenValue(refreshCookie);

				ResponseEntity<? extends TokenResponseDto> tokens = doRenew(refreshCookieValue);

				boolean rememberMe = CookieService.isRememberMe(refreshCookie);

				cookieService.createCookies(httpServletRequest, tokens.getBody(), rememberMe, cookies);

				// add cookies to response to update browser
				cookies.addCookiesTo(httpServletResponse);
			}

			// replace cookies in original request with new ones
			CookieCollection requestCookies = new CookieCollection(httpServletRequest.getCookies());
			requestCookies.add(cookies.getAccessTokenCookie());
			requestCookies.add(cookies.getRefreshTokenCookie());

			try {
				Class.forName("com.netflix.zuul.context.RequestContext", false, this.getClass().getClassLoader());
				// RequestContext context = RequestContext.getCurrentContext();

				// context.addZuulRequestHeader("Cookie", "access_token=" + cookies.getAccessTokenCookie().getValue());
			} catch (ClassNotFoundException e) {

			}

			return new CookiesHttpServletRequestWrapper(httpServletRequest, requestCookies.toArray());
		}
	}

	public abstract ResponseEntity<? extends TokenResponseDto> doRenew(String refreshToken);

	public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		cookieService.clearCookies(httpServletRequest, httpServletResponse);
	}

	public HttpServletRequest stripTokens(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = cookieService.stripCookies(httpServletRequest.getCookies());
		return new CookiesHttpServletRequestWrapper(httpServletRequest, cookies);
	}

	protected TokenCookies getCachedCookies(String refreshTokenValue) {
		synchronized (recentlyRefreshed) {
			TokenCookies ctx = recentlyRefreshed.get(refreshTokenValue);

			if (ctx == null) {
				ctx = new TokenCookies();
				recentlyRefreshed.put(refreshTokenValue, ctx);
			}

			return ctx;
		}
	}

}
