package com.assessment.projectmanagementservice.handler;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.EMPTY_STRING;
import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E001_SERVER_ERROR;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assessment.projectmanagementservice.exception.ErrorDetails;
import com.assessment.projectmanagementservice.exception.ProjectManagementException;

/**
 * @author Admin
 *
 */
@ControllerAdvice
@RestController
public class ProjectManagementResEntityExpHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagementResEntityExpHandler.class);

	/**
	 * @param ex<Exception>
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false),
				ERROR_E001_SERVER_ERROR);
		LOGGER.error(ex.getMessage());
		LOGGER.error(errorDetails.toString());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 * @param ex<TaskManagerException>
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(ProjectManagementException.class)
	public final ResponseEntity<ErrorDetails> handleTaskManagerException(ProjectManagementException ex,
			WebRequest request) {
		String messageId = ex.getMessageId();
		if ("".equalsIgnoreCase(messageId)) {
			messageId = ERROR_E001_SERVER_ERROR;
		}
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false),
				messageId);
		LOGGER.error(ex.getMessage());
		LOGGER.error(errorDetails.toString());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// List<String> details = new ArrayList<>();
		String firstMessage = EMPTY_STRING;
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			if (error.getDefaultMessage() != null && !EMPTY_STRING.equals(error.getDefaultMessage())) {
				firstMessage = error.getDefaultMessage();
				break;
			}
		}

		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false),
				firstMessage);
		LOGGER.error(ex.getMessage());
		LOGGER.error(errorDetails.toString());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}