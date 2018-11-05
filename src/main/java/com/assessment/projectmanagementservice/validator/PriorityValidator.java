package com.assessment.projectmanagementservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Priority Validator
 * 
 * @author manum
 *
 */
public class PriorityValidator implements ConstraintValidator<PriorityConstraint, Integer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PriorityValidator.class);

	@Override
	public void initialize(PriorityConstraint priority) {
	}

	@Override
	public boolean isValid(Integer priority, ConstraintValidatorContext cxt) {
		if (priority != null && (priority < 0 || priority > 30)) {
			LOGGER.error("PriorityValidator: priority should be > 0 and <= 30");
			return false;
		}
		return true;
	}

}