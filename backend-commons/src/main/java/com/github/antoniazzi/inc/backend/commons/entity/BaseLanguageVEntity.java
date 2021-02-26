package com.github.antoniazzi.inc.backend.commons.entity;

import java.util.Objects;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Base Entity Class to be extended by all Language Version Aware Entities
 * 
 * @version 1.0.0
 * @since 14.01.2020
 * @author Kristijan Georgiev
 */
@MappedSuperclass
public class BaseLanguageVEntity extends BaseVIdEntity implements LanguageAwareEntity {

	private static final long serialVersionUID = -7597170059309404867L;

	@NotNull
	@Size(min = 2, max = 8)
	protected String langKey;

	@NotNull
	@Size(max = 254)
	protected String name;

	@Size(max = 1024)
	protected String description;

	@Override
	public String getLangKey() {
		return langKey;
	}

	@Override
	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(langKey);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj.getClass().isInstance(this) || this.getClass().isInstance(obj)))
			return false;
		BaseLanguageVEntity other = (BaseLanguageVEntity) obj;
		return Objects.equals(langKey, other.langKey);
	}

}