package com.github.antoniazzi.inc.backend.commons.security.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenFilter;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProperties;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProvider;

/**
 * Basic Security Configuration Setup
 * 
 * @version 1.0.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnClass({ WebSecurityConfigurerAdapter.class, SecurityProblemSupport.class })
@ConditionalOnProperty(value = PropPrefix.WPSOFT_SECURITY_WEB_AUTO_CONFIGURATION, havingValue = "true")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);

	@Autowired
	private ApplicationContext context;

	@Autowired
	private CorsProperties corsProperties;

	@Autowired
	private CsrfProperties csrfProperties;

	@Autowired
	private TokenProperties tokenProperties;

	@Autowired
	private HeadersProperties headersProperties;

	@Autowired
	private HttpBasicAuthProperties httpBasicAuthProperties;

	@Autowired
	private SessionManagementProperties sessionManagementProperties;

	@Autowired
	private SecurityProblemSupport problemSupport;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Setting up Web Security");

		http.headers(h -> {
			h.contentSecurityPolicy(headersProperties.getContentSecurityPolicy());
			h.referrerPolicy(headersProperties.getReferrerPolicy());
			h.featurePolicy(headersProperties.getFeaturePolicy());

			switch (headersProperties.getFrameOptions()) {
			case DENY:
				h.frameOptions().deny();
				break;
			case DISABLE:
				h.frameOptions().disable();
				break;
			case SAME_ORIGIN:
				h.frameOptions().sameOrigin();
				break;
			}
		});

		if (csrfProperties.getEnabled() == true) {
			http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		} else {
			http.csrf().disable();
		}

		if (corsProperties.getEnabled() == true) {
			http.cors();
		} else {
			http.cors().disable();
		}

		if (httpBasicAuthProperties.getEnabled() == true) {
			http.httpBasic();
		} else {
			http.httpBasic().disable();
		}

		http.sessionManagement(s -> {
			s.sessionCreationPolicy(sessionManagementProperties.getSessionCreationPolicy());
		});

		http.authorizeRequests(a -> {
			a.antMatchers("/api/**").authenticated();
		});

		if (tokenProperties.getTokenFilter()) {
			http.addFilterBefore(new TokenFilter((TokenProvider) context.getBean(TokenProvider.class)),
					UsernamePasswordAuthenticationFilter.class);
		}

		http.exceptionHandling(e -> {
			e.authenticationEntryPoint(problemSupport);
			e.accessDeniedHandler(problemSupport);
		});
	}

}
