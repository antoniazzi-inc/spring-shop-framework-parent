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

import com.github.antoniazzi.inc.administrationms.entity.HelpContent;
import com.github.antoniazzi.inc.administrationms.repository.HelpContentLanguageRepository;
import com.github.antoniazzi.inc.administrationms.repository.HelpContentRepository;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.service.impl.EntityServiceImpl;

/**
 * HelpContentService
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@Service
@Transactional
public class HelpContentService extends EntityServiceImpl<HelpContent, Long, HelpContentRepository> {

	@Autowired
	private HelpContentLanguageRepository helpContentLanguageRepository;

	public HelpContentService(HelpContentRepository repository) {
		super(repository);
	}

	@Override
	public HelpContent create(HelpContent obj) {
		if (obj.getHelpContentLanguages() == null || obj.getHelpContentLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		obj.getHelpContentLanguages().forEach(l -> {
			l.setHelpContent(obj);
		});

		return super.create(obj);
	}

	@Override
	public HelpContent update(HelpContent obj) {
		if (obj.getHelpContentLanguages() == null || obj.getHelpContentLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		HelpContent entity = getOrThrow(obj.getId());
		entity.getHelpContentLanguages().removeAll(obj.getHelpContentLanguages());

		helpContentLanguageRepository.deleteAll(entity.getHelpContentLanguages());
		entity.setHelpContentLanguages(null);

		obj.getHelpContentLanguages().forEach(l -> {
			l.setHelpContent(obj);
		});

		return super.update(obj);
	}

}
