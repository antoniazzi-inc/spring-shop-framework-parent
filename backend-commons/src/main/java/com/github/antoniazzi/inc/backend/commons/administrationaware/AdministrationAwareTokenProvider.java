package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;
import com.github.antoniazzi.inc.backend.commons.security.token.AbstractTokenProvider;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProperties;
import com.github.antoniazzi.inc.backend.commons.security.token.TokensTransfer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Manages token Creation, Verification, User Extraction
 * 
 * @version 0.1.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
@Component
@ConditionalOnClass({ WebSecurityConfigurerAdapter.class, Jwts.class })
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ADMINISTRATION_AWARE_TOKEN_PROVIDER, havingValue = "true")
public class AdministrationAwareTokenProvider extends AbstractTokenProvider {

	private static final Logger log = LoggerFactory.getLogger(AdministrationAwareTokenProvider.class);

	{
		log.info("Registering Administration Aware Token Provider");
	}

	public static final String USER_ID_KEY = "user_id";
	public static final String USER_UID_KEY = "user_uid";

	public static final String LANGUAGE_KEY = "language_key";

	public static final String ADMINISTRATION_ID_KEY = "administration_id";
	public static final String ADMINISTRATION_UID_KEY = "administration_uid";

	public AdministrationAwareTokenProvider(TokenProperties tokenProperties) {
		super(tokenProperties);
	}

	protected TokensTransfer build(Authentication authentication) {
		TokensTransfer tokenTransfer = new TokensTransfer();

		AdministrationUserAuthentication administrationAwareUserAuthentication = (AdministrationUserAuthentication) authentication;

		List<String> authorities = administrationAwareUserAuthentication.getAuthorities().stream().map(a -> {
			return a.getAuthority();
		}).collect(Collectors.toList());

		long now = (new Date()).getTime();

		Long userId = administrationAwareUserAuthentication.getUserId();
		String langKey = administrationAwareUserAuthentication.getLanguageKey();
		String userUid = administrationAwareUserAuthentication.getUserUid();
		String accessTokenJti = UUID.randomUUID().toString();

		Date accessTokenValidity = new Date(now + this.tokenValidityInMilliseconds);

		// @formatter:off
			JwtBuilder accessTokenBuilder = Jwts.builder()
				.setId(accessTokenJti)
				.setSubject(authentication.getName())
				.setExpiration(accessTokenValidity)
				.setIssuedAt(new Date(now))
				.setHeaderParam(TYP_KEY, TYP_VALUE)
				.claim(AUTHORITIES_KEY, authorities)
				.claim(USER_ID_KEY, userId)
				.claim(LANGUAGE_KEY, langKey)
				.claim(USER_UID_KEY, userUid)
				.claim(ADMINISTRATION_ID_KEY, administrationAwareUserAuthentication.getAdministrationId())
				.claim(ADMINISTRATION_UID_KEY, administrationAwareUserAuthentication.getAdministrationUid())
				.signWith(key, SignatureAlgorithm.HS512);
		// @formatter:on

		tokenTransfer.setAccessTokenBuilder(accessTokenBuilder);

		// if (administrationAwareUserAuthentication.getRememberMe()) {
		String refreshTokenAti = accessTokenJti;
		String refreshTokenJti = UUID.randomUUID().toString();

		Date refreshTokenValidity = new Date(now + this.refreshTokenValidityInMilliseconds);

		// @formatter:off
				JwtBuilder refreshTokenBuilder = Jwts.builder()
					.setId(refreshTokenJti)
					.setSubject(authentication.getName())
					.setExpiration(refreshTokenValidity)
					.setIssuedAt(new Date(now))
					.setHeaderParam(TYP_KEY, TYP_VALUE)
					.claim(ATI_KEY, refreshTokenAti)
					.claim(AUTHORITIES_KEY, authorities)
					.claim(USER_ID_KEY, userId)
					.claim(LANGUAGE_KEY, langKey)
					.claim(USER_UID_KEY, userUid)
					.claim(ADMINISTRATION_ID_KEY, administrationAwareUserAuthentication.getAdministrationId())
					.claim(ADMINISTRATION_UID_KEY, administrationAwareUserAuthentication.getAdministrationUid())
					.signWith(key, SignatureAlgorithm.HS512);
			// @formatter:on

		tokenTransfer.setRefreshTokenBuilder(refreshTokenBuilder);
		// }

		return tokenTransfer;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = ((List<String>) claims.get(AUTHORITIES_KEY)).stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		AdministrationUserAuthentication result = new AdministrationUserAuthentication(authorities);

		result.setCredentials(token);
		result.setPrincipal(claims.getSubject());

		result.setAdministrationId(claims.get(ADMINISTRATION_ID_KEY, Long.class));
		result.setAdministrationUid(claims.get(ADMINISTRATION_UID_KEY, String.class));

		result.setUserId(claims.get(USER_ID_KEY, Long.class));
		result.setUserUid(claims.get(USER_UID_KEY, String.class));
		result.setLanguageKey(claims.get(LANGUAGE_KEY, String.class));

		result.setFromToken(true);

		return result;
	}

	public String resolveToken(Object requestRaw) {
		HttpServletRequest request = (HttpServletRequest) requestRaw;
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER_KEY);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		String jwt = request.getParameter(AUTHORIZATION_TOKEN_KEY);

		if (StringUtils.hasText(jwt)) {
			return jwt;
		}

		return null;
	}
}
