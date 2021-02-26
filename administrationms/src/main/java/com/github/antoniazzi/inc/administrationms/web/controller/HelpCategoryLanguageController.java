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
package com.github.antoniazzi.inc.administrationms.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.antoniazzi.inc.administrationms.entity.HelpCategoryLanguage;
import com.github.antoniazzi.inc.administrationms.service.HelpCategoryLanguageService;
import com.github.antoniazzi.inc.backend.commons.web.rest.EntityController;

/**
 * HelpCategoryLanguageController
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@RestController
@RequestMapping("/api/help-category-languages")
public class HelpCategoryLanguageController
		extends EntityController<HelpCategoryLanguage, Long, HelpCategoryLanguageService> {

	public HelpCategoryLanguageController(HelpCategoryLanguageService service) {
		super(service);
	}

}
