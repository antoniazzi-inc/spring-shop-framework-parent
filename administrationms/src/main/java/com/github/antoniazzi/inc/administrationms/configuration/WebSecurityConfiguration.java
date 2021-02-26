/*
 * Copyright 2018-2021 Antoniazzi Holding BV
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.antoniazzi.inc.administrationms.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.github.antoniazzi.inc.backend.commons.security.token.TokenFilter;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProperties;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProvider;
import com.github.antoniazzi.inc.backend.commons.security.web.CorsProperties;
import com.github.antoniazzi.inc.backend.commons.security.web.CsrfProperties;
import com.github.antoniazzi.inc.backend.commons.security.web.HeadersProperties;
import com.github.antoniazzi.inc.backend.commons.security.web.HttpBasicAuthProperties;
import com.github.antoniazzi.inc.backend.commons.security.web.SessionManagementProperties;

/**
 * WebSecurityConfiguration
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
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
			a.antMatchers("/api/administrations/unsecured/**").permitAll();
			a.antMatchers(HttpMethod.GET, "/api/countries/**").permitAll();
			a.antMatchers(HttpMethod.GET, "/api/langs/**").permitAll();
			a.antMatchers(HttpMethod.GET, "/api/time-zones/**").permitAll();
			a.antMatchers(HttpMethod.GET, "/api/tax-rules/**").permitAll();
			a.antMatchers(HttpMethod.GET, "/api/tax-rate-links/**").permitAll();
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
