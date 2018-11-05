package com.assessment.projectmanagementservice.handler;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E002_NOT_FOUND;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assessment.projectmanagementservice.controller.TaskController;
import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.Task;

public class ProjectManagementResEntityExpHandlerTest {

	private MockMvc mockMvc;

	@Mock
	TaskController taskController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(taskController)
				.setControllerAdvice(new ProjectManagementResEntityExpHandler()).build();
	}

	@Test
	public final void testHandleAllExceptions() throws Exception {
		Mockito.when(taskController.index()).thenThrow(new RuntimeException("Unexpected Exception"));
		mockMvc.perform(get("/api/task/")).andExpect(status().is(500)).andReturn();

	}
	
	@Test
	public final void testGetAllTasksException() throws Exception {
		Mockito.when(taskController.getAllTasks())
				.thenThrow(new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found"));
		mockMvc.perform(get("/api/task/get")).andExpect(status().isBadRequest());
	}

	@Test
	public final void testUpdateTaskException() throws Exception {
		Mockito.when(taskController.updateTask(new Task()))
				.thenThrow(new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found"));
		mockMvc.perform(put("/api/task/update")).andExpect(status().isBadRequest());

	}

	/*@Test
	public final void testDeleteTaskException() throws Exception {
		Mockito.when(taskController.deleteTask(Mockito.eq(1)))
				.thenThrow(new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found"));
		mockMvc.perform(delete("/api/task/1")).andExpect(status().isBadRequest());

	}*/

	@Test
	public final void testGetAllParentsException() throws Exception {
		Mockito.when(taskController.getAllParents())
				.thenThrow(new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found"));
		mockMvc.perform(get("/api/task/getParents")).andExpect(status().isBadRequest());

	}

}
