package com.assessment.projectmanagementservice.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.assessment.projectmanagementservice.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDateDeSerializerTest {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	Date startDate;
	Date endDate;

	@Before
	public void setUp() throws Exception {
		startDate = dateFormat.parse("30/03/2008");
		endDate = dateFormat.parse("30/04/2009");
	}

	@Test
	public final void testDeserializeJsonParserDeserializationContext() throws IOException {

		String testExampleTask = "{\"endDate\": \"30/04/2009\", "
				+ "\"parentTask\": { \"parentName\": \"Test Parent Task\", \"parentTaskId\": 1 },  "
				+ "\"priority\": 5,  \"startDate\": \"30/03/2008\",  \"taskId\": 2,  \"taskName\": \"Test Task\"}";

		Task task = new ObjectMapper().readValue(testExampleTask, Task.class);

		assertEquals(startDate, task.getStartDate());
		assertEquals(endDate, task.getEndDate());

	}

	@Test
	public final void testDeserializeJsonNullEmptyCheck() throws IOException {

		String testExampleTask = "{\"endDate\": null, "
				+ "\"parentTask\": { \"parentName\": \"Test Parent Task\", \"parentTaskId\": 1 },  "
				+ "\"priority\": 5,  \"startDate\": \"\",  \"taskId\": 2,  \"taskName\": \"Test Task\"}";

		Task task = new ObjectMapper().readValue(testExampleTask, Task.class);

		assertEquals(null, task.getStartDate());
		assertEquals(null, task.getEndDate());

	}
	
	@Test
	public final void testDeserializeJsonParseException() throws IOException {

		String testExampleTask = "{\"endDate\": null, "
				+ "\"parentTask\": { \"parentName\": \"Test Parent Task\", \"parentTaskId\": 1 },  "
				+ "\"priority\": 5,  \"startDate\": \"@df\",  \"taskId\": 2,  \"taskName\": \"Test Task\"}";

		Task task = new ObjectMapper().readValue(testExampleTask, Task.class);

		assertEquals(null, task.getStartDate());		

	}

}
