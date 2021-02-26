package com.github.antoniazzi.inc.backend.commons.security.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.model.dto.request.BaseRequestDto;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenResponseDto;
import com.github.antoniazzi.inc.backend.commons.web.rest.BaseController;

public abstract class AbstractCookieAuthenticationController<T extends BaseRequestDto> extends BaseController {

	@Autowired
	protected CookieService cookieService;

	@Autowired
	protected CookieAuthenticationService<T> cookieAuthenticationService;

	protected ResponseEntity<? extends TokenResponseDto> authenticate(T dto, HttpServletRequest request, HttpServletResponse response) {
		return cookieAuthenticationService.authenticate(dto, request, response);
	}

	protected ResponseEntity<?> forceRenew(HttpServletRequest request, HttpServletResponse response) {
		Cookie accessCookie = CookieService.getAccessTokenCookie(request);
		Cookie refreshCookie = CookieService.getRefreshTokenCookie(request);

		if (accessCookie == null && refreshCookie != null) {
			try {
				cookieAuthenticationService.refreshToken(request, response, refreshCookie);
			} catch (Exception e) {
				throw new AccessDeniedException("Can not refresh token");
			}
		} else {
			cookieService.clearCookies(request, response);
			throw new BadRequestException("Access Token Cookie provided or no Refresh Token Cookie provided");
		}

		return ResponseEntity.ok().build();
	}

	protected ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		cookieAuthenticationService.logout(request, response);
		return ResponseEntity.ok().build();
	}

}
