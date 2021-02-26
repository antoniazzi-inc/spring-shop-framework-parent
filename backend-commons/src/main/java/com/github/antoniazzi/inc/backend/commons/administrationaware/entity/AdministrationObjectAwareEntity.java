package com.github.antoniazzi.inc.backend.commons.administrationaware.entity;

/**
 * Interface to be implemented by all Administration Object Aware Entities
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */

public interface AdministrationObjectAwareEntity<T, C> extends AdministrationAwareEntity {

	public T getAdministration();

	public void setAdministration(T administration);

	public C getAdministrationClass();

}
