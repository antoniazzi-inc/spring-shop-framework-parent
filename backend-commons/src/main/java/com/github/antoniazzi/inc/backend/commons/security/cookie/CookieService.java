package com.github.antoniazzi.inc.backend.commons.security.cookie;

import static org.apache.http.conn.util.InetAddressUtils.isIPv4Address;
import static org.apache.http.conn.util.InetAddressUtils.isIPv6Address;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.antoniazzi.inc.backend.commons.security.token.AbstractTokenProvider;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProvider;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenResponseDto;

@Service
@ConditionalOnClass(HttpServletRequest.class)
public class CookieService {

	private static final List<String> COOKIE_NAMES = Arrays.asList(TokenProvider.AUTHORIZATION_TOKEN_KEY,
			TokenProvider.AUTHORIZATION_TOKEN_REFRESH_KEY, TokenProvider.AUTHORIZATION_TOKEN_SESSION_KEY);
	/**
	 * Number of seconds to expire refresh token cookies before the enclosed token
	 * expires. This makes sure we don't run into race conditions where the cookie
	 * is still there but expires while we process it.
	 */
	private static final long REFRESH_TOKEN_EXPIRATION_WINDOW_SECS = 10L;

	private final Logger log = LoggerFactory.getLogger(CookieService.class);

	/**
	 * Public suffix matcher (to strip private subdomains off cookie scope).
	 */
	private PublicSuffixMatcher suffixMatcher;

	public CookieService() {
		// Alternatively, always get an up-to-date list by passing an URL
		this.suffixMatcher = PublicSuffixMatcherLoader.getDefault();
	}

	/**
	 * Get a cookie by name from the given servlet request.
	 *
	 * @param request    the request containing the cookie.
	 * @param cookieName the case-sensitive name of the cookie to get.
	 * @return the resulting Cookie; or null, if not found.
	 */
	private static Cookie getCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(cookieName)) {
					String value = cookie.getValue();
					if (StringUtils.hasText(value)) {
						return cookie;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Create cookies using the provided values.
	 *
	 * @param request     the request we are handling.
	 * @param accessToken the access token and enclosed refresh token for our
	 *                    cookies.
	 * @param rememberMe  whether the user had originally checked "remember me".
	 * @param result      will get the resulting cookies set.
	 */
	public void createCookies(HttpServletRequest request, TokenResponseDto tokens, boolean rememberMe,
			TokenCookies result) {
		String domain = getCookieDomain(request);
		log.debug("creating cookies for domain {}", domain);
		Cookie accessTokenCookie = new Cookie(TokenProvider.AUTHORIZATION_TOKEN_KEY, tokens.getAccessToken());
		setCookieProperties(accessTokenCookie, request.isSecure(), domain);
		log.debug("created access token cookie '{}'", accessTokenCookie.getName());

		Cookie refreshTokenCookie = createRefreshTokenCookie(tokens, rememberMe);
		setCookieProperties(refreshTokenCookie, request.isSecure(), domain);
		log.debug("created refresh token cookie '{}', age: {}", refreshTokenCookie.getName(),
				refreshTokenCookie.getMaxAge());

		result.setCookies(accessTokenCookie, refreshTokenCookie);
	}

	/**
	 * Create a cookie out of the given refresh token. Refresh token cookies contain
	 * the base64 encoded refresh token (a JWT token). They also contain a hint
	 * whether the refresh token was for remember me or not. If not, then the cookie
	 * will be prefixed by the timestamp it was created at followed by a pipe '|'.
	 * This gives us the chance to expire session cookies regardless of the token
	 * duration.
	 */
	private Cookie createRefreshTokenCookie(TokenResponseDto tokens, boolean rememberMe) {
		int maxAge = -1;
		String name = TokenProvider.AUTHORIZATION_TOKEN_SESSION_KEY;
		String value = tokens.getRefreshToken();
		if (rememberMe) {
			name = TokenProvider.AUTHORIZATION_TOKEN_REFRESH_KEY;
			// get expiration in seconds from the token's "exp" claim
			Integer exp = Math.toIntExact(tokens.getRefreshTokenExpiresIn());
			if (exp != null) {
				maxAge = exp;
				log.debug("refresh token valid for another {} secs", maxAge);
				// let cookie expire a bit earlier than the token to avoid race conditions
				maxAge -= REFRESH_TOKEN_EXPIRATION_WINDOW_SECS;
			}
		}
		Cookie refreshTokenCookie = new Cookie(name, value);
		refreshTokenCookie.setMaxAge(maxAge);
		return refreshTokenCookie;
	}

	public static Cookie getAccessTokenCookie(HttpServletRequest request) {
		return getCookie(request, TokenProvider.AUTHORIZATION_TOKEN_KEY);
	}

	public static Cookie getRefreshTokenCookie(HttpServletRequest request) {
		Cookie cookie = getCookie(request, TokenProvider.AUTHORIZATION_TOKEN_REFRESH_KEY);
		if (cookie == null) {
			cookie = getCookie(request, TokenProvider.AUTHORIZATION_TOKEN_SESSION_KEY);
		}
		return cookie;
	}

	/**
	 * Extracts the refresh token from the refresh token cookie. Since we encode
	 * additional information into the cookie, this needs to be called to get hold
	 * of the enclosed JWT.
	 *
	 * @param refreshCookie the cookie we store the value in.
	 * @return the refresh JWT from the cookie.
	 */
	public static String getRefreshTokenValue(Cookie refreshCookie) {
		String value = refreshCookie.getValue();
		int i = value.indexOf('|');
		if (i > 0) {
			return value.substring(i + 1);
		}
		return value;
	}

	/**
	 * Returns true if the refresh token cookie was set with remember me checked. We
	 * can recognize this by the name of the cookie.
	 *
	 * @param refreshTokenCookie the cookie holding the refresh token.
	 * @return true, if it was set persistently (i.e. for "remember me").
	 */
	public static boolean isRememberMe(Cookie refreshTokenCookie) {
		return refreshTokenCookie.getName().equals(TokenProvider.AUTHORIZATION_TOKEN_REFRESH_KEY);
	}
	
    public boolean isSessionExpired(Cookie refreshCookie) {
        if (isRememberMe(refreshCookie)) {       //no session expiration for "remember me"
            return false;
        }
        //read non-remember-me session length in secs
        int validity = 1800;
        if (validity < 0) {           //no session expiration configured
            return false;
        }
        Integer iat = (Integer) AbstractTokenProvider.getClaim(refreshCookie.getValue(), "iat");
        if (iat == null) {           //token creating timestamp in secs is missing, session does not expire
            return false;
        }
        int now = (int) (System.currentTimeMillis() / 1000L);
        int sessionDuration = now - iat;
        log.debug("session duration {} secs, will timeout at {}", sessionDuration, validity);
        return sessionDuration > validity;            //session has expired
    }

	/**
	 * Set cookie properties of access and refresh tokens.
	 *
	 * @param cookie   the cookie to modify.
	 * @param isSecure whether it is coming from a secure request.
	 * @param domain   the domain for which the cookie is valid. If null, then will
	 *                 fall back to default.
	 */
	private void setCookieProperties(Cookie cookie, boolean isSecure, String domain) {
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setSecure(isSecure); // if the request comes per HTTPS set the secure option on the cookie
		if (domain != null) {
			cookie.setDomain(domain);
		}
	}

	/**
	 * Logs the user out by clearing all cookies.
	 *
	 * @param httpServletRequest  the request containing the Cookies.
	 * @param httpServletResponse the response used to clear them.
	 */
	public void clearCookies(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String domain = getCookieDomain(httpServletRequest);
		for (String cookieName : COOKIE_NAMES) {
			clearCookie(httpServletRequest, httpServletResponse, domain, cookieName);
		}
	}

	private void clearCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String domain, String cookieName) {
		Cookie cookie = new Cookie(cookieName, "");
		setCookieProperties(cookie, httpServletRequest.isSecure(), domain);
		cookie.setMaxAge(0);
		httpServletResponse.addCookie(cookie);
		log.debug("clearing cookie {}", cookie.getName());
	}

	/**
	 * Returns the top level domain of the server from the request. This is used to
	 * limit the Cookie to the top domain instead of the full domain name.
	 * <p>
	 * A lot of times, individual gateways of the same domain get their own
	 * subdomain but authentication shall work across all subdomains of the top
	 * level domain.
	 * <p>
	 * For example, when sending a request to <code>app1.domain.com</code>, this
	 * returns <code>.domain.com</code>.
	 *
	 * @param request the HTTP request we received from the client.
	 * @return the top level domain to set the cookies for. Returns null if the
	 *         domain is not under a public suffix (.com, .co.uk), e.g. for
	 *         localhost.
	 */
	private String getCookieDomain(HttpServletRequest request) {
		String domain = null;

		// if not explicitly defined, use top-level domain
		domain = request.getServerName().toLowerCase();
		// strip off leading www.
		if (domain.startsWith("www.")) {
			domain = domain.substring(4);
		}
		// if it isn't an IP address
		if (!isIPv4Address(domain) && !isIPv6Address(domain)) {
			// strip off private subdomains, leaving public TLD only
			String suffix = suffixMatcher.getDomainRoot(domain);
			if (suffix != null && !suffix.equals(domain)) {
				// preserve leading dot
				// return "." + suffix;
				return suffix;
			}
		}
		// no top-level domain, stick with default domain
		return null;
	}

	/**
	 * Strip our token cookies from the array.
	 *
	 * @param cookies the cookies we receive as input.
	 * @return the new cookie array without our tokens.
	 */
	@SuppressWarnings("unlikely-arg-type")
	public Cookie[] stripCookies(Cookie[] cookies) {
		CookieCollection cc = new CookieCollection(cookies);
		if (cc.removeAll(COOKIE_NAMES)) {
			return cc.toArray();
		}
		return cookies;
	}

}
