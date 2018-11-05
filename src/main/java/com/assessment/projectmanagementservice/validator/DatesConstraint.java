package com.assessment.projectmanagementservice.validator;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E008_START_DATE_AFTER_END_DATE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author manum
 *
 */

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TaskDateValidator.class, ProjectDateValidator.class})
public @interface DatesConstraint {
	String message() default ERROR_E008_START_DATE_AFTER_END_DATE;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}