package com.assessment.projectmanagementservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assessment.projectmanagementservice.model.ParentTask;
import com.assessment.projectmanagementservice.model.Task;
import com.assessment.projectmanagementservice.service.TaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TaskController.class, secure = false)
public class TaskControllerTest {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService taskService;

	Task mockTask = new Task();
	Task mockTask2 = new Task();
	Task mockTask3 = new Task();

	@Before
	public void setup() throws ParseException {
		createMockTask();
		createMockTask1();
	}

	private void createMockTask() throws ParseException {
		mockTask.setTaskId(2);
		mockTask.setTaskName("Test Task");
		mockTask.setStartDate(dateFormat.parse("30/03/2008"));
		mockTask.setEndDate(dateFormat.parse("30/04/2009"));
		mockTask.setPriority(5);
		ParentTask parentTask = new ParentTask("Test Parent Task");
		parentTask.setParentTaskId(1);
		mockTask.setParentTask(parentTask);
	}

	private void createMockTask1() throws ParseException {
		mockTask2.setTaskId(3);
		mockTask2.setTaskName("Test Task 1");
		mockTask2.setStartDate(dateFormat.parse("30/03/2012"));
		mockTask2.setEndDate(dateFormat.parse("30/04/2013"));
		mockTask2.setPriority(6);
		ParentTask parentTask = new ParentTask("Test Parent Task 1");
		parentTask.setParentTaskId(2);
		mockTask2.setParentTask(parentTask);
	}

	private String testExampleTask = "{\"endDate\": \"30/04/2009\", "
			+ "\"parentTask\": { \"parentName\": \"Test Parent Task\", \"parentTaskId\": 1 },  "
			+ "\"priority\": 5,  \"startDate\": \"30/03/2008\",  \"taskId\": 2,  \"taskName\": \"Test Task\"}";

	@Test
	public void testGetTaskById() throws Exception {
		Mockito.when(taskService.findTaskById(Mockito.eq(2))).thenReturn(mockTask);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/task/2").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(testExampleTask, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetTaskByIdasNull() throws Exception {
		Mockito.when(taskService.findTaskById(Mockito.eq(2))).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/task/2").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void taskNotFoundTest() throws Exception {
		mockMvc.perform(get("/api/task/6").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateTask() throws Exception {		
		// taskService.addTask to respond back with mockTask
		Mockito.when(taskService.addTask(Mockito.any(Task.class))).thenReturn(mockTask);

		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/task/create")
				.accept(MediaType.APPLICATION_JSON).content(testExampleTask).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/api/task/2", response.getHeader(HttpHeaders.LOCATION));

	}

	@Test
	public void testCreateTaskInvalidPriority() throws Exception {
		String testExampleTask = "{\"endDate\": \"30/04/2009\", \"parentName\": null,  "
				+ "\"parentTask\": { \"parentName\": \"Test Parent Task\", \"parentTaskId\": 1 },  "
				+ "\"priority\": 34,  \"startDate\": \"30/03/2008\",  \"taskId\": 2,  \"taskName\": \"Test Task\"}";

		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/task/create")
				.accept(MediaType.APPLICATION_JSON).content(testExampleTask).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	public void testCreateTaskInvalidDate() throws Exception {
		String testExampleTask = "{\"endDate\": \"30/04/2001\", \"parentName\": null,  "
				+ "\"parentTask\": { \"parentName\": \"Test Parent Task\", \"parentTaskId\": 1 },  "
				+ "\"priority\": 20,  \"startDate\": \"30/05/2008\",  \"taskId\": 2,  \"taskName\": \"Test Task\"}";

		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/task/create")
				.accept(MediaType.APPLICATION_JSON).content(testExampleTask).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	public void testGetAllTasks() throws Exception {

		when(taskService.getTasks()).thenReturn(Arrays.asList(mockTask, mockTask2));

		mockMvc.perform(get("/api/task/get")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].taskId", is(2)))
				.andExpect(jsonPath("$[0].taskName", is("Test Task")))
				.andExpect(jsonPath("$[0].startDate", is("30/03/2008"))).andExpect(jsonPath("$[1].taskId", is(3)))
				.andExpect(jsonPath("$[1].taskName", is("Test Task 1")))
				.andExpect(jsonPath("$[1].startDate", is("30/03/2012"))).andExpect(jsonPath("$[1].priority", is(6)));

		verify(taskService, times(1)).getTasks();
		verifyNoMoreInteractions(taskService);

	}
	

	@Test
	public void testUpdateTask() throws Exception {

		Mockito.when(taskService.findTaskById(Mockito.eq(2))).thenReturn(mockTask);
		// Mockito.when(taskService.update(Mockito.any(Task.class),
		// Mockito.eq(2))).thenReturn(mockTask);
		Mockito.when(taskService.update(Mockito.any(Task.class))).thenReturn(mockTask);

		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/task/update")
				.accept(MediaType.APPLICATION_JSON).content(testExampleTask).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testUpdateTaskByIdasNull() throws Exception {
		Mockito.when(taskService.findTaskById(Mockito.eq(2))).thenReturn(null);

		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/task/update")
				.accept(MediaType.APPLICATION_JSON).content(testExampleTask).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void testGetAllParents() throws Exception {

		when(taskService.getParentTasks())
				.thenReturn(Arrays.asList(mockTask.getParentTask(), mockTask2.getParentTask()));

		mockMvc.perform(get("/api/task/getParents")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].parentTaskId", is(1)))
				.andExpect(jsonPath("$[0].parentName", is("Test Parent Task")))
				.andExpect(jsonPath("$[1].parentName", is("Test Parent Task 1")));

		verify(taskService, times(1)).getParentTasks();
		verifyNoMoreInteractions(taskService);
	}

	@Test
	public final void testGetAllParentsByNull() throws Exception {
		when(taskService.getParentTasks()).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/task/getParents")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void testEndTask() throws Exception {

		Mockito.when(taskService.findTaskById(Mockito.eq(4))).thenReturn(mockTask3);
		Mockito.when(taskService.endTask(Mockito.any(Task.class))).thenReturn(mockTask3);

		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/task/endTask/4")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testEndTaskByNull() throws Exception {
		Mockito.when(taskService.findTaskById(Mockito.eq(4))).thenReturn(null);
		// Send task as body to /api/task/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/task/endTask/4")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

}
