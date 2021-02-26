package com.github.antoniazzi.inc.backend.commons.eh;

/**
 * Static Error Messages
 * 
 * @version 0.1.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public final class ExceptionConst {

	public static final String ID_MUST_BE_NULL = "ID must be NULL";
	public static final String ID_MUST_NOT_BE_NULL = "ID must be specified";

	public static final String NOT_IMPLEMENTED_EX = "UnsupportedOperationException";
	public static final String NOT_ACCEPTABLE_EX = "HttpMediaTypeNotAcceptableException";
	public static final String UNSUPPORTED_MEDIA_TYPE_EX = "HttpMediaTypeNotSupportedException";
	public static final String METHOD_NOT_ALLOWED_EX = "HttpRequestMethodNotSupportedException";
	public static final String GATEWAY_TIMEOUT_EX = "SocketTimeoutException";
	public static final String NOT_FOUND_EX = "NoHandlerFoundException";
	public static final String UNAUTHORIZED_EX = "AuthenticationException";
	public static final String FORBIDDEN_EX = "AccessDeniedException";
	public static final String BAD_REQUEST_EX = "BadRequestException";

	private ExceptionConst() {

	}

}
