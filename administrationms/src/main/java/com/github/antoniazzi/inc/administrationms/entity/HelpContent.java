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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.antoniazzi.inc.administrationms.entity.enumeration.HelpType;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * HelpContent
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
public class HelpContent extends BaseVIdEntity {

	private static final long serialVersionUID = 1L;
	private Long id;

	@Size(max = 40)
	private String fieldCode;

	@Size(max = 40)
	private String screenCode;

	@Size(max = 40)
	private String tabCode;

	@Size(max = 40)
	private String popupCode;

	@NotNull
	@Enumerated(EnumType.STRING)
	private HelpType helpType;

	@JsonIgnoreProperties("helpContent")
	@OneToMany(mappedBy = "helpContent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<HelpContentLanguage> helpContentLanguages = new HashSet<>();

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "help_content_category", joinColumns = @JoinColumn(name = "help_contents_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
	private Set<HelpCategory> categories = new HashSet<>();

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "help_content_tag", joinColumns = @JoinColumn(name = "help_contents_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
	private Set<HelpTag> tags = new HashSet<>();

	public HelpContent addHelpContentLanguage(HelpContentLanguage helpContentLanguage) {
		this.helpContentLanguages.add(helpContentLanguage);
		helpContentLanguage.setHelpContent(this);
		return this;
	}

	public HelpContent removeHelpContentLanguage(HelpContentLanguage helpContentLanguage) {
		this.helpContentLanguages.remove(helpContentLanguage);
		helpContentLanguage.setHelpContent(null);
		return this;
	}

	public HelpContent addCategory(HelpCategory helpCategory) {
		this.categories.add(helpCategory);
		helpCategory.getContents().add(this);
		return this;
	}

	public HelpContent removeCategory(HelpCategory helpCategory) {
		this.categories.remove(helpCategory);
		helpCategory.getContents().remove(this);
		return this;
	}

	public HelpContent addTag(HelpTag helpTag) {
		this.tags.add(helpTag);
		helpTag.getContents().add(this);
		return this;
	}

	public HelpContent removeTag(HelpTag helpTag) {
		this.tags.remove(helpTag);
		helpTag.getContents().remove(this);
		return this;
	}

}
