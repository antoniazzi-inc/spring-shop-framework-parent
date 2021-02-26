package com.github.antoniazzi.inc.backend.commons;

public class PropPrefix {

	public static final String WPSOFT = "wpsoft";

	public static final String ENABLED = ".enabled";

	// START ASYNC PROPERTIES

	public static final String WPSOFT_ASYNC = WPSOFT + ".async";

	public static final String WPSOFT_ASYNC_ENABLED = WPSOFT_ASYNC + ENABLED;

	public static final String WPSOFT_ASYNC_MODE = WPSOFT_ASYNC + ".mode";
	public static final String WPSOFT_ASYNC_PROXY_TARGET_CLASS = WPSOFT_ASYNC + ".proxyTargetClass";

	public static final String WPSOFT_ASYNC_TASK_EXECUTOR = WPSOFT_ASYNC + ".task-executor";
	public static final String WPSOFT_ASYNC_SECURITY_TASK_EXECUTOR = WPSOFT_ASYNC + ".security-task-executor";

	// END ASYNC PROPERTIES

	// START WPSOFT GENERAL PROPERTIES

	public static final String WPSOFT_SCHEDULING = WPSOFT + ".scheduling";

	// END WPSOFT GENERAL PROPERTIES

	// START JACKSON MODULES PROPERTIES

	public static final String WPSOFT_JACKSON_MODULES = WPSOFT + ".jackson-modules";

	public static final String WPSOFT_JACKSON_MODULES_ENABLED = WPSOFT_JACKSON_MODULES + ENABLED;

	public static final String WPSOFT_JACKSON_MODULES_HIBERNATE5 = WPSOFT_JACKSON_MODULES + ".hibernate5";

	public static final String WPSOFT_JACKSON_MODULES_HPPC = WPSOFT_JACKSON_MODULES + ".hppc";

	public static final String WPSOFT_JACKSON_MODULES_PROBLEM = WPSOFT_JACKSON_MODULES + ".problem";

	public static final String WPSOFT_JACKSON_MODULES_CONSTRAINT_PROBLEM_VIOLATION = WPSOFT_JACKSON_MODULES + ".constraint-violation-problem";

	// END JACKSON MODULES PROPERTIES

	// START SECURITY PROPERTIES

	public static final String WPSOFT_SECURITY = WPSOFT + ".security";

	public static final String WPSOFT_SECURITY_ENABLED = WPSOFT_SECURITY + ENABLED;

	// START SECURITY TOKEN PROPERTIES

	public static final String WPSOFT_SECURITY_TOKEN = WPSOFT_SECURITY + ".token";

	// END SECURITY TOKEN PROPERTIES

	// START SECURITY WEB PROPERTIES

	public static final String WPSOFT_SECURITY_WEB = WPSOFT_SECURITY + ".web";

	public static final String WPSOFT_SECURITY_WEB_AUTO_CONFIGURATION = WPSOFT_SECURITY_WEB + ".auto-configuration";

	public static final String WPSOFT_SECURITY_WEB_CORS = WPSOFT_SECURITY_WEB + ".cors";
	public static final String WPSOFT_SECURITY_WEB_CORS_ENABLED = WPSOFT_SECURITY_WEB_CORS + ENABLED;

	public static final String WPSOFT_SECURITY_WEB_CSRF = WPSOFT_SECURITY_WEB + ".csrf";
	public static final String WPSOFT_SECURITY_WEB_CSRF_ENABLED = WPSOFT_SECURITY_WEB_CSRF + ENABLED;

	public static final String WPSOFT_SECURITY_WEB_HEADERS = WPSOFT_SECURITY_WEB + ".headers";

	public static final String WPSOFT_SECURITY_WEB_HTTP_BASIC_AUTH = WPSOFT_SECURITY_WEB + ".http-basic-auth";
	public static final String WPSOFT_SECURITY_WEB_HTTP_BASIC_AUTH_ENABLED = WPSOFT_SECURITY_WEB_HTTP_BASIC_AUTH + ENABLED;

	public static final String WPSOFT_SECURITY_WEB_SESSION_MANAGEMENT = WPSOFT_SECURITY + ".session-management";

	// END SECURITY WEB PROPERTIES

	// END SECURITY PROPERTIES

	// START ADMINISTRATION AWARE PROPERTIES

	public static final String WPSOFT_ADMINISTRATION_AWARE = WPSOFT + ".administration-aware";
	public static final String WPSOFT_ADMINISTRATION_AWARE_ENABLED = WPSOFT_ADMINISTRATION_AWARE + ENABLED;
	public static final String WPSOFT_ADMINISTRATION_AWARE_ASPECT = WPSOFT_ADMINISTRATION_AWARE + ".aspect";
	public static final String WPSOFT_ADMINISTRATION_AWARE_SECURITY_SERVICE = WPSOFT_ADMINISTRATION_AWARE + ".security-service";
	public static final String WPSOFT_ADMINISTRATION_AWARE_TOKEN_PROVIDER = WPSOFT_ADMINISTRATION_AWARE + ".token-provider";
	public static final String WPSOFT_ADMINISTRATION_AWARE_AUTHENTICATION_PROVIDER = WPSOFT_ADMINISTRATION_AWARE + ".authentication-provider";
	public static final String WPSOFT_ADMINISTRATION_AWARE_TOKEN_AUTHENTICATION_PROVIDER = WPSOFT_ADMINISTRATION_AWARE + ".token-authentication-provider";

	// END ADMINISTRATION AWARE PROPERTIES

	// START WPSOFT ERROR HANDLING PROPERTIES

	public static final String WPSOFT_ERROR_HANDLING = WPSOFT + ".error-handling";
	public static final String WPSOFT_ERROR_HANDLING_ENABLED = WPSOFT_ERROR_HANDLING + ENABLED;

	// END WPSOFT ERROR HANDLING PROPERTIES

	// START QUEUE PROPERTIES

	public static final String WPSOFT_QUEUE = WPSOFT + ".queue";

	public static final String WPSOFT_QUEUE_ENABLED = WPSOFT_QUEUE + ENABLED;

	public static final String WPSOFT_QUEUE_ASPECT = WPSOFT_QUEUE + ".aspect";

	public static final String WPSOFT_QUEUE_DESTINATIONS = WPSOFT_QUEUE + ".destinations";

	// END QUEUE PROPERTIES

	// START WPSOFT ORDER ENGINE HANDLING PROPERTIES

	public static final String WPSOFT_ORDER_ENGINE = WPSOFT + ".order-engine";
	public static final String WPSOFT_ORDER_ENGINE_ENABLED = WPSOFT_ORDER_ENGINE + ENABLED;

	// END WPSOFT ORDER ENGINE PROPERTIES

}
