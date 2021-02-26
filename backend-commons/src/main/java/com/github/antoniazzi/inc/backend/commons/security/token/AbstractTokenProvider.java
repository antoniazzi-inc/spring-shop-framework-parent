package com.github.antoniazzi.inc.backend.commons.security.token;

import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Manages token Creation, Verification, User Extraction
 * 
 * @version 1.0.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
public abstract class AbstractTokenProvider implements TokenProvider {

	private static final Logger log = LoggerFactory.getLogger(AbstractTokenProvider.class);

	protected static Key key;

	protected long tokenValidityInMilliseconds;
	protected long refreshTokenValidityInMilliseconds;

	protected final TokenProperties tokenProperties;

	protected abstract TokensTransfer build(Authentication authentication);

	public AbstractTokenProvider(TokenProperties tokenProperties) {
		this.tokenProperties = tokenProperties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getBase64Secret());

		key = Keys.hmacShaKeyFor(keyBytes);

		this.tokenValidityInMilliseconds = 1000 * tokenProperties.getAccessTokenValidityInSeconds();
		this.refreshTokenValidityInMilliseconds = 1000 * tokenProperties.getRefreshTokenValidityInSeconds();
	}

	@Override
	public TokenResponseDto createToken(Authentication authentication) {
		TokensTransfer tokenTransfer = build(authentication);

		Long expiresIn = this.tokenValidityInMilliseconds / 1000 - 1;

		TokenResponseDto response = new TokenResponseDto(expiresIn, tokenTransfer.getAccessTokenBuilder().compact());

		if (tokenTransfer.getRefreshTokenBuilder() != null) {
			Long refreshTokenExpiresIn = this.refreshTokenValidityInMilliseconds / 1000 - 1;

			response.setRefreshTokenExpiresIn(refreshTokenExpiresIn);
			response.setRefreshToken(tokenTransfer.getRefreshTokenBuilder().compact());
		}

		return response;
	}

	public static Object getClaim(String authToken, String claimKey) {

		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken).getBody();

		return claims.get(claimKey);
	}

	@Override
	public boolean validateToken(String authToken) {
		return validateToken(authToken, true);
	}

	@Override
	public boolean validateToken(String token, boolean isAccessToken) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

			if (isAccessToken && claims.containsKey(ATI_KEY)) {
				throw new UnsupportedJwtException("Refresh JWT provided.");
			}

			if (!isAccessToken && !claims.containsKey(ATI_KEY)) {
				throw new UnsupportedJwtException("Access JWT provided.");
			}

			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.debug("Invalid JWT signature.");
			log.trace("Invalid JWT signature trace: {}", e);
		} catch (ExpiredJwtException e) {
			log.debug("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			log.debug("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			log.debug("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
		}

		return false;
	}
	
}
