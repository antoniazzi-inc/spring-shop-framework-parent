package com.github.antoniazzi.inc.backend.commons.entity;

/**
 * Interface to be implemented by all Language Aware Entities
 * 
 * @version 1.0.0
 * @since 14.01.2020
 * @author Kristijan Georgiev
 */
public interface LanguageAwareEntity {

	public String getLangKey();

	public void setLangKey(String langKey);

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

}
