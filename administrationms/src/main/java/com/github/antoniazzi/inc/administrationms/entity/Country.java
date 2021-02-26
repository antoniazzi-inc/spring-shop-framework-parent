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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Country
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
public class Country extends BaseVIdEntity {
	private static final long serialVersionUID = 1L;

	@Size(max = 2)
	private String iso;

	@Size(max = 3)
	private String iso3;

	@Size(max = 10)
	private String numCode;

	@Size(max = 10)
	private String phoneCode;

	@NotNull
	@Size(max = 254)
	private String nlName;

	@NotNull
	@Size(max = 254)
	@Column(unique = true)
	private String enName;

	@JsonIgnore
	@JsonIgnoreProperties("country")
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Administration> administrations = new HashSet<>();

	@JsonIgnore
	@JsonIgnoreProperties("country")
	@OneToMany(mappedBy = "country")
	private Set<TaxRate> taxRates = new HashSet<>();

	@JsonIgnore
	@JsonIgnoreProperties("country")
	@OneToMany(mappedBy = "country")
	private Set<TaxRule> taxRules = new HashSet<>();

	public Country addAdministration(Administration administration) {
		this.administrations.add(administration);
		administration.setCountry(this);
		return this;
	}

	public Country removeAdministration(Administration administration) {
		this.administrations.remove(administration);
		administration.setCountry(null);
		return this;
	}

	public Country addTaxRate(TaxRate taxRate) {
		this.taxRates.add(taxRate);
		taxRate.setCountry(this);
		return this;
	}

	public Country removeTaxRate(TaxRate taxRate) {
		this.taxRates.remove(taxRate);
		taxRate.setCountry(null);
		return this;
	}

	public Country addTaxRule(TaxRule taxRule) {
		this.taxRules.add(taxRule);
		taxRule.setCountry(this);
		return this;
	}

	public Country removeTaxRule(TaxRule taxRule) {
		this.taxRules.remove(taxRule);
		taxRule.setCountry(null);
		return this;
	}

}
