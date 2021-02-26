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

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.antoniazzi.inc.backend.commons.administrationaware.AdministrationAwareEntityListener;
import com.github.antoniazzi.inc.backend.commons.administrationaware.entity.AdministrationAwareEntity;
import com.github.antoniazzi.inc.backend.commons.entity.BaseVIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TaxRate
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
public class TaxRate extends BaseVIdEntity implements AdministrationAwareEntity {

	private static final long serialVersionUID = 1L;

	public static final Integer HIGH = 5;
	public static final Integer MEDIUM_HIGH = 4;
	public static final Integer MEDIUM_LOW = 3;
	public static final Integer LOW = 2;
	public static final Integer ZERO = 1;

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(name = "wp_level")
	private Integer level;

	@NotNull
	@Column(name = "rate", precision = 10, scale = 2)
	private BigDecimal rate;

	@NotNull
	private ZonedDateTime validFrom;

	private ZonedDateTime validTo;

	@OneToMany(mappedBy = "fromTaxRate")
	private Set<TaxRateLink> fromTaxRateLinks = new HashSet<>();

	@OneToMany(mappedBy = "toTaxRate")
	private Set<TaxRateLink> toTaxRateLinks = new HashSet<>();

	@ManyToOne
	@JsonIgnore
	private Administration administration;

	@NotNull
	@ManyToOne(optional = false)
	@JsonIgnoreProperties("taxRates")
	private Country country;

	@Override
	public Long getAdministrationId() {
		return administration != null ? administration.getId() : null;
	}

	@Override
	public void setAdministrationId(Long administrationId) {
		administration = new Administration(administrationId, -1l);
	}

	public TaxRate addFromTaxRateLink(TaxRateLink taxRateLink) {
		this.fromTaxRateLinks.add(taxRateLink);
		taxRateLink.setFromTaxRate(this);
		return this;
	}

	public TaxRate removeFromTaxRateLink(TaxRateLink taxRateLink) {
		this.fromTaxRateLinks.remove(taxRateLink);
		taxRateLink.setFromTaxRate(null);
		return this;
	}

	public TaxRate addToTaxRateLink(TaxRateLink taxRateLink) {
		this.toTaxRateLinks.add(taxRateLink);
		taxRateLink.setToTaxRate(this);
		return this;
	}

	public TaxRate removeToTaxRateLink(TaxRateLink taxRateLink) {
		this.toTaxRateLinks.remove(taxRateLink);
		taxRateLink.setToTaxRate(null);
		return this;
	}

}
