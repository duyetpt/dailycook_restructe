package org.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})

/**
 * 
 * @author duyetpt
 * this anotation can use for setter, getter method or property.
 */
public @interface JsonProperty {
	
	public String value() default "";
}
