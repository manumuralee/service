package com.assessment.projectmanagementservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.projectmanagementservice.model.Task;

/**
 * 
 * @author manum
 *
 */
public class TaskDateValidator implements ConstraintValidator<DatesConstraint, Task> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskDateValidator.class);

	@Override
	public void initialize(DatesConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Task task, ConstraintValidatorContext context) {
		if (task != null && task.getEndDate() != null && task.getStartDate() != null
				&& task.getEndDate().before(task.getStartDate())) {
			LOGGER.error("DateValidator: Start date should be before End Date");
			return false;
		}
		return true;
	}

}