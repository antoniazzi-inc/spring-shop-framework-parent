package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.entity.LongIdAwareEntity;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenProvider;

/**
 * User Authentication Provider for Custom Authentication
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */

@Component
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(AdministrationUserTokenAuthenticationProvider.class)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ADMINISTRATION_AWARE_AUTHENTICATION_PROVIDER, havingValue = "true")
public class AdministrationUserAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(AdministrationUserAuthenticationProvider.class);

	{
		log.info("Registering Administration User Authentication Provider");
	}

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdministrationSecurityUserRepository administrationAwareUserRepository;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AdministrationUserAuthentication administrationAwareUserAuthentication = (AdministrationUserAuthentication) authentication;

		if (administrationAwareUserAuthentication.isFromToken()) {
			return administrationAwareUserAuthentication;
		}

		final String username = administrationAwareUserAuthentication.getPrincipal().toString();
		final String password = administrationAwareUserAuthentication.getCredentials().toString();
		final String administrationKey = administrationAwareUserAuthentication.getAdministrationKey();
		Long administrationId = administrationAwareUserAuthentication.getAdministrationId();

		if (administrationKey != null && administrationId != null) {
			throw new BadRequestException("sho prajme");
		}

		if (administrationKey != null) {
			Optional<LongIdAwareEntity> oA = ((SecurityAdministrationRepository) context
					.getBean(SecurityAdministrationRepository.class)).findByAdministrationKey(administrationKey);
			if (oA.isPresent()) {
				administrationId = (Long) oA.get().getId();
			} else {
				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		}

		return administrationAwareUserRepository.findByUsernameAndAdministrationId(username, administrationId)
				.map(user -> {
					if (StringUtils.hasText(password) && this.tokenProvider.validateToken(password, false)) {

					} else if (!passwordEncoder.matches(password, user.getPassword())) {
						throw new UsernameNotFoundException("User " + username + " was not found in the database");
					}

					if (!user.getEnabled()) {
						throw new UsernameNotFoundException("User " + username + " was not enabled");
					}

					AdministrationUserAuthentication result = new AdministrationUserAuthentication(
							user.getAuthorities());
					result.setAdministrationId(user.getAdministrationId());

					result.setUserId(user.getId());
					result.setUserUid(user.getUid());

					result.setLanguageKey(user.getLanguageKey());

					result.setPrincipal(username);
					result.setCredentials(password);

					result.setRememberMe(administrationAwareUserAuthentication.getRememberMe());

					result.setAdministrationUid(administrationAwareUserAuthentication.getAdministrationUid());

					return result;
				}).orElseThrow(
						() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(AdministrationUserAuthentication.class);
	}

}
