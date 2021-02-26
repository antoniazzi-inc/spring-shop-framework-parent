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

import com.github.antoniazzi.inc.administrationms.entity.DeliveryMethod;
import com.github.antoniazzi.inc.administrationms.repository.DeliveryMethodLanguageRepository;
import com.github.antoniazzi.inc.administrationms.repository.DeliveryMethodRepository;
import com.github.antoniazzi.inc.backend.commons.administrationaware.AdministrationAwareService;
import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;
import com.github.antoniazzi.inc.backend.commons.service.impl.EntityServiceImpl;

/**
 * DeliveryMethodService
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@Service
@Transactional
@AdministrationAwareService
public class DeliveryMethodService extends EntityServiceImpl<DeliveryMethod, Long, DeliveryMethodRepository> {

	@Autowired
	private DeliveryMethodLanguageRepository deliveryMethodLanguageRepository;

	public DeliveryMethodService(DeliveryMethodRepository repository) {
		super(repository);
	}

	@Override
	public DeliveryMethod create(DeliveryMethod obj) {
		if (obj.getDeliveryMethodLanguages() == null || obj.getDeliveryMethodLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		obj.getDeliveryMethodLanguages().forEach(l -> {
			l.setDeliveryMethod(obj);
		});

		return super.create(obj);
	}

	@Override
	public DeliveryMethod update(DeliveryMethod obj) {
		if (obj.getDeliveryMethodLanguages() == null || obj.getDeliveryMethodLanguages().isEmpty()) {
			throw new BadRequestException("Languages not provided");
		}

		DeliveryMethod entity = getOrThrow(obj.getId());
		entity.getDeliveryMethodLanguages().removeAll(obj.getDeliveryMethodLanguages());

		deliveryMethodLanguageRepository.deleteAll(entity.getDeliveryMethodLanguages());
		entity.setDeliveryMethodLanguages(null);

		obj.getDeliveryMethodLanguages().forEach(l -> {
			l.setDeliveryMethod(obj);
		});

		return super.update(obj);
	}

}
