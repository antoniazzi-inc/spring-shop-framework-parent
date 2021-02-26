package com.github.antoniazzi.inc.backend.commons.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.NativeWebRequest;

import com.github.antoniazzi.inc.backend.commons.eh.NotFoundException;

/**
 * Base Controller Class to be extended by all Controllers
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public abstract class BaseController {

	@Autowired
	protected NativeWebRequest request;

	protected NotFoundException out() {
		return new NotFoundException(request);
	}

}
