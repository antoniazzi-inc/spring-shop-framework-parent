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
package com.github.antoniazzi.inc.administrationms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.antoniazzi.inc.administrationms.entity.enumeration.DeliveryMethodType;
import com.github.antoniazzi.inc.backend.commons.administrationaware.AdministrationAwareEntityListener;
import com.github.antoniazzi.inc.backend.commons.administrationaware.entity.AdministrationAwareEntity;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DeliveryMethod
 *
 * @version 1.0.0
 * @since 22.02.2021
 * @author Kristijan Georgiev
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners({ AdministrationAwareEntityListener.class })
@Filter(name = "ADMINISTRATION_FILTER", condition = "administration_id = :administrationId")
@FilterDef(name = "ADMINISTRATION_FILTER", parameters = { @ParamDef(name = "administrationId", type = "long") })
public class DeliveryMethod extends BaseVIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	private DeliveryMethodType deliveryMethodType;

	@Column(name = "config_json", columnDefinition = "json")
	private String configJson;

	@ManyToOne
	@JsonIgnore
	private Administration administration;

	@JsonIgnoreProperties("deliveryMethod")
	@OneToMany(mappedBy = "deliveryMethod", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DeliveryMethodLanguage> deliveryMethodLanguages = new HashSet<>();

	@Override
	public Long getAdministrationId() {
		return administration != null ? administration.getId() : null;
	}

	@Override
	public void setAdministrationId(Long administrationId) {
		administration = new Administration(administrationId, -1l);
	}

	public DeliveryMethod addDeliveryMethodLanguage(DeliveryMethodLanguage deliveryMethodLanguage) {
		this.deliveryMethodLanguages.add(deliveryMethodLanguage);
		deliveryMethodLanguage.setDeliveryMethod(this);
		return this;
	}

	public DeliveryMethod removeDeliveryMethodLanguage(DeliveryMethodLanguage deliveryMethodLanguage) {
		this.deliveryMethodLanguages.remove(deliveryMethodLanguage);
		deliveryMethodLanguage.setDeliveryMethod(null);
		return this;
	}

}
