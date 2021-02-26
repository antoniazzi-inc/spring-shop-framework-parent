package com.github.antoniazzi.inc.backend.commons.security.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * General Security Properties
 * 
 * @version 1.0.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_TOKEN, ignoreUnknownFields = false)
public class TokenProperties {

	private Boolean tokenFilter = true;

	private String base64Secret = "FDCJ/nXiD5jy5senvFIMEr3QQoHAxkbZDb4n2MPV9/9ykcOTskTDAVt7rJcch4X9lEyse58gqw6L9CYvZnOADw==";
	private Integer accessTokenValidityInSeconds = 300;
	private Integer refreshTokenValidityInSeconds = 604800;

	public Boolean getTokenFilter() {
		return tokenFilter;
	}

	public void setTokenFilter(Boolean tokenFilter) {
		this.tokenFilter = tokenFilter;
	}

	public String getBase64Secret() {
		return base64Secret;
	}

	public void setBase64Secret(String base64Secret) {
		this.base64Secret = base64Secret;
	}

	public Integer getAccessTokenValidityInSeconds() {
		return accessTokenValidityInSeconds;
	}

	public void setAccessTokenValidityInSeconds(Integer accessTokenValidityInSeconds) {
		this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
	}

	public Integer getRefreshTokenValidityInSeconds() {
		return refreshTokenValidityInSeconds;
	}

	public void setRefreshTokenValidityInSeconds(Integer refreshTokenValidityInSeconds) {
		this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
	}

}