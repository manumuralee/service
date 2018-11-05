package com.assessment.projectmanagementservice.validator;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E007_INVALID_PRIORITY;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Priority Constraint
 * 
 * @author manum
 *
 */
@Documented
@Constraint(validatedBy = PriorityValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PriorityConstraint {
	String message() default ERROR_E007_INVALID_PRIORITY;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}