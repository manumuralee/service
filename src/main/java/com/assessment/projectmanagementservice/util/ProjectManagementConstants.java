/**
 * 
 */
package com.assessment.projectmanagementservice.util;

/**
 * @author manum
 *
 */
public interface ProjectManagementConstants {

	public static final String EMPTY_STRING = "";

	// Error
	public static final String ERROR_E001_SERVER_ERROR = "E001";
	public static final String ERROR_E002_NOT_FOUND = "E002";
	public static final String ERROR_E003_DUPLICATE = "E003";
	public static final String ERROR_E004_SAVE_FAILED = "E004";
	public static final String ERROR_E005_UPDATE_FAILED = "E005";
	public static final String ERROR_E006_DELETE_FAILED = "E006";
	public static final String ERROR_E007_INVALID_PRIORITY = "E007";
	public static final String ERROR_E008_INVALID_DATE = "E008";
	public static final String ERROR_E008_START_DATE_AFTER_END_DATE = "E008";

	// Search Operation constants
	public static final String EQUALITY = ":";
	public static final String NEGATION = "!";
	public static final String GREATER_THAN = ">";
	public static final String LESS_THAN = "<";
	public static final String LIKE = "~";

}
