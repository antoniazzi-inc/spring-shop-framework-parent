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
package com.github.antoniazzi.inc.administrationms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.antoniazzi.inc.administrationms.entity.HelpCategory;
import com.github.antoniazzi.inc.administrationms.repository.HelpCategoryLanguageRepository;
import com.github.antoniazzi.inc.administrationms.repository.HelpCategoryRepository;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.service.impl.EntityServiceImpl;

/**
 * HelpCategoryService
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@Service
@Transactional
public class HelpCategoryService extends EntityServiceImpl<HelpCategory, Long, HelpCategoryRepository> {

	@Autowired
	private HelpCategoryLanguageRepository helpCategoryLanguageRepository;

	public HelpCategoryService(HelpCategoryRepository repository) {
		super(repository);
	}

	@Override
	public HelpCategory create(HelpCategory obj) {
		if (obj.getHelpCategoryLanguages() == null || obj.getHelpCategoryLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		obj.getHelpCategoryLanguages().forEach(l -> {
			l.setHelpCategory(obj);
		});

		return super.create(obj);
	}

	@Override
	public HelpCategory update(HelpCategory obj) {
		if (obj.getHelpCategoryLanguages() == null || obj.getHelpCategoryLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		HelpCategory entity = getOrThrow(obj.getId());
		entity.getHelpCategoryLanguages().removeAll(obj.getHelpCategoryLanguages());

		helpCategoryLanguageRepository.deleteAll(entity.getHelpCategoryLanguages());
		entity.setHelpCategoryLanguages(null);

		obj.getHelpCategoryLanguages().forEach(l -> {
			l.setHelpCategory(obj);
		});

		return super.update(obj);
	}

	@Override
	public void delete(Long id) {
		HelpCategory e = getOrThrow(id);

		e.getContents().forEach(c -> {
			e.removeContent(c);
		});

		if (e.getParent() != null) {
			e.getParent().removeChild(e);
		}

		super.delete(e);
	}

}
