package com.github.antoniazzi.inc.backend.commons.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.github.antoniazzi.inc.backend.commons.web.rest.BaseController;

/**
 * Abstract Authentication Controller
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */
public abstract class AbstractAuthenticationController extends BaseController {

	@Autowired
	protected TokenProvider tokenProvider;

	@Autowired
	protected TokenAuthenticationHelper tokenAuthenticationHelper;

	protected abstract ResponseEntity<TokenResponseDto> renew(String refreshToken);

}
