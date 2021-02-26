package com.github.antoniazzi.inc.backend.commons.security.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Configuration Property Class for setting the application headers.
 * 
 * If nothing overridden defaults are set.
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_WEB_HEADERS, ignoreUnknownFields = false)
public class HeadersProperties {

	private String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";
	private String featurePolicy = "geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'";
	private ReferrerPolicyHeaderWriter.ReferrerPolicy referrerPolicy = ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN;
	private FrameOptions frameOptions = FrameOptions.DENY;

	public String getContentSecurityPolicy() {
		return contentSecurityPolicy;
	}

	public void setContentSecurityPolicy(String contentSecurityPolicy) {
		this.contentSecurityPolicy = contentSecurityPolicy;
	}

	public String getFeaturePolicy() {
		return featurePolicy;
	}

	public void setFeaturePolicy(String featurePolicy) {
		this.featurePolicy = featurePolicy;
	}

	public ReferrerPolicyHeaderWriter.ReferrerPolicy getReferrerPolicy() {
		return referrerPolicy;
	}

	public void setReferrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy referrerPolicy) {
		this.referrerPolicy = referrerPolicy;
	}

	public FrameOptions getFrameOptions() {
		return frameOptions;
	}

	public void setFrameOptions(FrameOptions frameOptions) {
		this.frameOptions = frameOptions;
	}

}
