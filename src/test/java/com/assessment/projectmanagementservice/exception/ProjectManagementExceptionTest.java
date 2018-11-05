/**
 * 
 */
package com.assessment.projectmanagementservice.exception;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author manum
 *
 */
public class ProjectManagementExceptionTest {

	ProjectManagementException taskManagerException;

	@Before
	public void setup() throws ParseException {
		taskManagerException = new ProjectManagementException();
	}

	/**
	 * Test method for
	 * {@link com.ProjectManagementException.taskmanagerservice.exception.TaskManagerException#setMessage(java.lang.String)}.
	 */
	@Test
	public final void testSetMessage() {
		taskManagerException.setMessage("Task Not Found");
		assertEquals(taskManagerException.getMessage(), "Task Not Found");
	}

	/**
	 * Test method for
	 * {@link com.ProjectManagementException.taskmanagerservice.exception.TaskManagerException#setMessageId(java.lang.String)}.
	 */
	@Test
	public final void testSetMessageId() {
		taskManagerException.setMessageId("301");
		assertEquals(taskManagerException.getMessageId(), "301");
	}

	/**
	 * Test method for
	 * {@link com.ProjectManagementException.taskmanagerservice.exception.TaskManagerException#setException(java.lang.String)}.
	 */
	@Test
	public final void testSetException() {
		taskManagerException.setException("Task Not Found");
		assertEquals(taskManagerException.getException(), "Task Not Found");
	}

}
