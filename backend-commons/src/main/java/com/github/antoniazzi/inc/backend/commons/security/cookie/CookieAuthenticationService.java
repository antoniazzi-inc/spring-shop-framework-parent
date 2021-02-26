package com.github.antoniazzi.inc.backend.commons.security.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.github.antoniazzi.inc.backend.commons.model.dto.request.BaseRequestDto;
import com.github.antoniazzi.inc.backend.commons.security.token.TokenResponseDto;

public interface CookieAuthenticationService<T extends BaseRequestDto> {

	public ResponseEntity<? extends TokenResponseDto> authenticate(T dto, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse);

	public ResponseEntity<? extends TokenResponseDto> returnTokenResponseWithSecurityCookies(
			ResponseEntity<TokenResponseDto> tokens, boolean rememberMe, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse);

	public void setSecurityCookies(ResponseEntity<TokenResponseDto> tokens, TokenCookies cookies, boolean rememberMe,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

	public HttpServletRequest refreshToken(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Cookie refreshCookie);

	public ResponseEntity<? extends TokenResponseDto> doRenew(String refreshToken);

	public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

	public HttpServletRequest stripTokens(HttpServletRequest httpServletRequest);

}
