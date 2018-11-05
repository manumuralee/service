/**
 * 
 */
package com.assessment.projectmanagementservice.exception;

/**
 * @author manum
 *
 */
public class ProjectManagementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private String messageId;
	private String exception;

	public ProjectManagementException() {
		super();
	}

	/**
	 * @param message
	 * @param messageId
	 * @param exception
	 */
	public ProjectManagementException(String message, String messageId, String exception) {
		super(exception);
		this.message = message;
		this.messageId = messageId;
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
