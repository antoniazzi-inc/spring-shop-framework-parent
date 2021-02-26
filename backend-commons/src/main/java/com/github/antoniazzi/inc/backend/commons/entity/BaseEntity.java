package com.github.antoniazzi.inc.backend.commons.entity;

import java.beans.FeatureDescriptor;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

/**
 * Base Entity Class to be extended by all Entities
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 * 
 * @param <ID> The ID Type
 */
@MappedSuperclass
@JsonInclude(Include.NON_NULL)
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonStringType.class), @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
public abstract class BaseEntity<ID extends Serializable> implements Serializable, IdAwareEntity {

	private static final long serialVersionUID = 8395068479075843549L;

	@CreationTimestamp
	@Column(updatable = false)
	@JsonProperty(access = Access.READ_ONLY)
	protected ZonedDateTime createdOn;

	@UpdateTimestamp
	@JsonProperty(access = Access.READ_ONLY)
	protected ZonedDateTime updatedOn;

	public BaseEntity() {

	}

	@Override
	public abstract ID getId();

	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public ZonedDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(ZonedDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * Returns a String[] of all NULL fields and the ones annotated with Access.READ_ONLY
	 * 
	 * @param source entity
	 * @return list of property names to be ignored
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> {
			JsonProperty jp = wrappedSource.getPropertyTypeDescriptor(propertyName).getAnnotation(JsonProperty.class);

			if (jp != null && jp.access().equals(Access.READ_ONLY)) {
				return true;
			}

			return wrappedSource.getPropertyValue(propertyName) == null;
		}).toArray(String[]::new);
	}

	/**
	 * Always generate hash from ID
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	/**
	 * Always compare entities by ID
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj.getClass().isInstance(this) || this.getClass().isInstance(obj)))
			return false;
		BaseEntity<ID> other = (BaseEntity<ID>) obj;
		return Objects.equals(getId(), other.getId());
	}

}
