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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.antoniazzi.inc.administrationms.entity.enumeration.ExternalSystemStatus;
import com.github.antoniazzi.inc.backend.commons.administrationaware.AdministrationAwareEntityListener;
import com.github.antoniazzi.inc.backend.commons.administrationaware.entity.AdministrationAwareEntity;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ExternalSystem
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
@Table(uniqueConstraints = { @UniqueConstraint(name = "uq_setting_key_administration_id", columnNames = { "settingKey",
		"administration_id" }) })
@FilterDef(name = "ADMINISTRATION_FILTER", parameters = { @ParamDef(name = "administrationId", type = "long") })
public class ExternalSystem extends BaseVIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 254)
	private String settingKey;

	@Type(type = "json")
	@Column(name = "setting_value_json", columnDefinition = "json")
	private String settingValueJson;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ExternalSystemStatus status;

	@ManyToOne
	@JsonIgnore
	private Administration administration;

	@Override
	public Long getAdministrationId() {
		return administration != null ? administration.getId() : null;
	}

	@Override
	public void setAdministrationId(Long administrationId) {
		administration = new Administration(administrationId, -1l);
	}

}
