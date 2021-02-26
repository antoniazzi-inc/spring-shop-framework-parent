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

import com.github.antoniazzi.inc.administrationms.entity.HelpTag;
import com.github.antoniazzi.inc.administrationms.repository.HelpTagLanguageRepository;
import com.github.antoniazzi.inc.administrationms.repository.HelpTagRepository;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.service.impl.EntityServiceImpl;

/**
 * HelpTagService
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@Service
@Transactional
public class HelpTagService extends EntityServiceImpl<HelpTag, Long, HelpTagRepository> {

	@Autowired
	private HelpTagLanguageRepository helpTagLanguageRepository;

	public HelpTagService(HelpTagRepository repository) {
		super(repository);
	}

	@Override
	public HelpTag create(HelpTag obj) {
		if (obj.getHelpTagLanguages() == null || obj.getHelpTagLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		obj.getHelpTagLanguages().forEach(l -> {
			l.setHelpTag(obj);
		});

		return super.create(obj);
	}

	@Override
	public HelpTag update(HelpTag obj) {
		if (obj.getHelpTagLanguages() == null || obj.getHelpTagLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		HelpTag entity = getOrThrow(obj.getId());
		entity.getHelpTagLanguages().removeAll(obj.getHelpTagLanguages());

		helpTagLanguageRepository.deleteAll(entity.getHelpTagLanguages());
		entity.setHelpTagLanguages(null);

		obj.getHelpTagLanguages().forEach(l -> {
			l.setHelpTag(obj);
		});

		return super.update(obj);
	}

	@Override
	public void delete(Long id) {
		HelpTag e = getOrThrow(id);

		e.getContents().forEach(c -> {
			e.removeContent(c);
		});

		super.delete(e);
	}

}
