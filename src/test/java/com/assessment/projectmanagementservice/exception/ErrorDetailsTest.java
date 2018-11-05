/**
 * 
 */
package com.assessment.projectmanagementservice.exception;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * @author manum
 *
 */
public class ErrorDetailsTest {
	
	ErrorDetails errorDetails;

	@Before
	public void setup() throws ParseException {
		errorDetails = new ErrorDetails();
	}

	/**
	 * Test method for {@link com.casestudy.taskmanagerservice.exception.ErrorDetails#setTimestamp(java.util.Date)}.
	 */
	@Test
	public final void testSetTimestamp() {
		Date timestamp = new Date();
		errorDetails.setTimestamp(timestamp);
		assertEquals(errorDetails.getTimestamp(), timestamp);
		
	}

	/**
	 * Test method for {@link com.casestudy.taskmanagerservice.exception.ErrorDetails#setMessage(java.lang.String)}.
	 */
	@Test
	public final void testSetMessage() {
		errorDetails.setMessage("Task Not Found");
		assertEquals(errorDetails.getMessage(), "Task Not Found");
	}

	/**
	 * Test method for {@link com.casestudy.taskmanagerservice.exception.ErrorDetails#setMessageId(java.lang.String)}.
	 */
	@Test
	public final void testSetMessageId() {
		errorDetails.setMessageId("301");
		assertEquals(errorDetails.getMessageId(), "301");
	}

	/**
	 * Test method for {@link com.casestudy.taskmanagerservice.exception.ErrorDetails#setDetails(java.lang.String)}.
	 */
	@Test
	public final void testSetDetails() {
		errorDetails.setDetails("Task Not Found");
		assertEquals(errorDetails.getDetails(), "Task Not Found");
	}

}
