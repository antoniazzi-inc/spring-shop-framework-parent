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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * HelpTag
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
public class HelpTag extends BaseVIdEntity {

	private static final long serialVersionUID = 1L;

	@Size(max = 7)
	private String color;

	@JsonIgnoreProperties("helpTag")
	@OneToMany(mappedBy = "helpTag", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<HelpTagLanguage> helpTagLanguages = new HashSet<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "tags")
	private Set<HelpContent> contents = new HashSet<>();

	public HelpTag addHelpTagLanguage(HelpTagLanguage helpTagLanguage) {
		this.helpTagLanguages.add(helpTagLanguage);
		helpTagLanguage.setHelpTag(this);
		return this;
	}

	public HelpTag removeHelpTagLanguage(HelpTagLanguage helpTagLanguage) {
		this.helpTagLanguages.remove(helpTagLanguage);
		helpTagLanguage.setHelpTag(null);
		return this;
	}

	public HelpTag addContent(HelpContent helpContent) {
		this.contents.add(helpContent);
		helpContent.getTags().add(this);
		return this;
	}

	public HelpTag removeContent(HelpContent helpContent) {
		this.contents.remove(helpContent);
		helpContent.getTags().remove(this);
		return this;
	}

}
