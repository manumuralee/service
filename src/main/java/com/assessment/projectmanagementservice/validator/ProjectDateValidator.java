package com.assessment.projectmanagementservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.projectmanagementservice.model.Project;

/**
 * 
 * @author manum
 *
 */
public class ProjectDateValidator implements ConstraintValidator<DatesConstraint, Project> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectDateValidator.class);

	@Override
	public void initialize(DatesConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Project project, ConstraintValidatorContext context) {
		if (project != null) {
			if ((project.getEndDate() != null && project.getStartDate() == null)
					|| (project.getEndDate() == null && project.getStartDate() != null)) {
				LOGGER.error("DateValidator: Start and End Date should not be null");
				return false;
			}
			if (project.getEndDate().before(project.getStartDate())) {
				LOGGER.error("DateValidator: Start date should be before End Date");
				return false;
			}
		}
		return true;
	}

}