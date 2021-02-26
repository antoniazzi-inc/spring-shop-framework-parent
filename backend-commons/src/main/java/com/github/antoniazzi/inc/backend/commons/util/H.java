package com.github.antoniazzi.inc.backend.commons.util;

import java.util.Collection;

public class H {

	public static boolean NullOrEmpty(Collection<?> c) {
		if (c == null || c.isEmpty()) {
			return true;
		}

		return false;
	}

}
