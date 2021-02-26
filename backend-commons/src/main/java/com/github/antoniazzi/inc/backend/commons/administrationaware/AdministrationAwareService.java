package com.github.antoniazzi.inc.backend.commons.administrationaware;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @AdministrationAwareService annotation to be used instead of Spring @Service
 * 
 * @version 1.0.0
 * @since 09.11.2019
 * @author Kristijan Georgiev
 */
@Service
@Component
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AdministrationAwareService {

}
