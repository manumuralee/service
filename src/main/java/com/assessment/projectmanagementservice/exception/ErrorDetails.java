package com.assessment.projectmanagementservice.exception;

import java.util.Date;

public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String messageId;
	private String details;
	
	/**
	 * 
	 */
	public ErrorDetails() {
		super();
	}

	/**
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public ErrorDetails(Date timestamp, String message, String details, String messageId) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.messageId = messageId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorDetails [timestamp=");
		builder.append(timestamp);
		builder.append(", message=");
		builder.append(message);
		builder.append(", messageId=");
		builder.append(messageId);
		builder.append(", details=");
		builder.append(details);
		builder.append("]");
		return builder.toString();
	}	

}