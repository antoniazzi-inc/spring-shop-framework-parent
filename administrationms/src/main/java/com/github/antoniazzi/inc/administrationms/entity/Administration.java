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

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.antoniazzi.inc.backend.commons.administrationaware.AdministrationAwareEntityListener;
import com.github.antoniazzi.inc.backend.commons.administrationaware.entity.AdministrationAwareEntity;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Administration
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
@Filter(name = "ADMINISTRATION_FILTER", condition = "id = :administrationId")
@FilterDef(name = "ADMINISTRATION_FILTER", parameters = { @ParamDef(name = "administrationId", type = "long") })
public class Administration extends BaseVIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = 1L;

	private String uid = UUID.randomUUID().toString();

	@NotNull
	@Size(max = 254)
	private String name;

	@NotNull
	@Size(max = 254)
	private String accessCode;

	@NotNull
	@Column(name = "wp_locked", nullable = false)
	private Boolean locked;

	@NotNull
	private Boolean useShop;

	@NotNull
	private Boolean useAutomation;

	@NotNull
	private Boolean trial;

	@NotNull
	private Integer relationsLimit;

	@NotNull
	private ZonedDateTime validFrom;

	private ZonedDateTime validTo;

	private String langKey;

	private String currency = "EUR";

	@JsonIgnoreProperties("administration")
	@OneToOne(mappedBy = "administration", cascade = CascadeType.ALL)
	private AdministrationBusiness administrationBusiness;

	@ManyToOne
	@JsonIgnoreProperties("administrations")
	private Country country;

	@JsonIgnoreProperties("administration")
	@OneToMany(mappedBy = "administration", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<AdministrationSettings> administrationSettings = new HashSet<>();

	@JsonIgnoreProperties("administration")
	@OneToMany(mappedBy = "administration", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Category> categories = new HashSet<>();

	@JsonIgnoreProperties("administration")
	@OneToMany(mappedBy = "administration", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Tag> tags = new HashSet<>();

	@JsonIgnoreProperties("administration")
	@OneToMany(mappedBy = "administration", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ExternalSystem> externalSystems = new HashSet<>();

	public Administration(Long id) {
		super(id);
	}

	public Administration(Long id, Long version) {
		super(id, version);
	}

	@Override
	public Long getAdministrationId() {
		return id;
	}

	@Override
	public void setAdministrationId(Long administrationId) {
		this.id = administrationId;
	}

	public Administration addAdministrationSettings(AdministrationSettings administrationSettings) {
		this.administrationSettings.add(administrationSettings);
		administrationSettings.setAdministration(this);
		return this;
	}

	public Administration removeAdministrationSettings(AdministrationSettings administrationSettings) {
		this.administrationSettings.remove(administrationSettings);
		administrationSettings.setAdministration(null);
		return this;
	}

	public Administration addCategory(Category category) {
		this.categories.add(category);
		category.setAdministration(this);
		return this;
	}

	public Administration removeCategory(Category category) {
		this.categories.remove(category);
		category.setAdministration(null);
		return this;
	}

	public Administration addTag(Tag tag) {
		this.tags.add(tag);
		tag.setAdministration(this);
		return this;
	}

	public Administration removeTag(Tag tag) {
		this.tags.remove(tag);
		tag.setAdministration(null);
		return this;
	}

	public Administration addExternalSystem(ExternalSystem externalSystem) {
		this.externalSystems.add(externalSystem);
		externalSystem.setAdministration(this);
		return this;
	}

	public Administration removeExternalSystem(ExternalSystem externalSystem) {
		this.externalSystems.remove(externalSystem);
		externalSystem.setAdministration(null);
		return this;
	}

	@Override
	public String toString() {
		return "Administration [name=" + name + ", accessCode=" + accessCode + ", locked=" + locked + ", useShop="
				+ useShop + ", useAutomation=" + useAutomation + ", trial=" + trial + ", relationsLimit="
				+ relationsLimit + ", validFrom=" + validFrom + ", validTo=" + validTo + ", langKey=" + langKey
				+ ", country=" + country + ", administrationSettings=" + administrationSettings + ", categories="
				+ categories + ", tags=" + tags + ", externalSystems=" + externalSystems + "]";
	}

}
