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

import com.assessment.projectmanagementservice.model.Project;
import com.assessment.projectmanagementservice.service.ProjectService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectController.class, secure = false)
public class ProjectControllerTest {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;

	Project project1 = new Project();
	Project project2 = new Project();

	@Before
	public void setUp() throws Exception {

		project1 = new Project();
		project1.setProjectId(1);
		project1.setProjectName("Project 1");
		project1.setStartDate(dateFormat.parse("30/03/2008"));
		project1.setEndDate(dateFormat.parse("30/04/2009"));
		project1.setPriority(5);

		project2 = new Project();
		project2.setProjectId(2);
		project2.setProjectName("Project 2");
		project2.setStartDate(dateFormat.parse("30/03/2011"));
		project2.setEndDate(dateFormat.parse("30/04/2012"));
		project2.setPriority(6);
	}

	private String testProject = "{\"endDate\": \"30/04/2009\", "
			+ "\"priority\": 5,  \"startDate\": \"30/03/2008\",  \"projectId\": 1,  \"projectName\": \"Project 1\"}";

	@Test
	public final void testGetProjectById() throws Exception {
		Mockito.when(projectService.findProjectById(Mockito.eq(1))).thenReturn(project1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(testProject, result.getResponse().getContentAsString(), false);
	}

	@Test
	public final void testGetProjectByIdByNull() throws Exception {
		Mockito.when(projectService.findProjectById(Mockito.eq(1))).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void projectNotFoundTest() throws Exception {
		mockMvc.perform(get("/api/project/6").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public final void testCreateProject() throws Exception {
		Mockito.when(projectService.addProject(Mockito.any(Project.class))).thenReturn(project1);
		// Send project as body to /api/project/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/create")
				.accept(MediaType.APPLICATION_JSON).content(testProject).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/api/project/1", response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void testCreateProjectInvalidPriority() throws Exception {
		String testProject = "{\"endDate\": \"30/04/2009\", "
				+ "\"priority\": 35,  \"startDate\": \"30/03/2008\",  \"projectId\": 1,  \"projectName\": \"Project 1\"}";
		// Send project as body to /api/project/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/create")
				.accept(MediaType.APPLICATION_JSON).content(testProject).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	public void testCreateProjectInvalidDate() throws Exception {
		String testProject = "{\"endDate\": \"30/04/2009\", "
				+ "\"priority\": 5,  \"startDate\": \"30/05/2010\",  \"projectId\": 1,  \"projectName\": \"Project 1\"}";

		// Send project as body to /api/project/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/create")
				.accept(MediaType.APPLICATION_JSON).content(testProject).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
	
	@Test
	public void testCreateProjectInvalidDateWithNull() throws Exception {
		String testProject = "{\"endDate\": null, "
				+ "\"priority\": 5,  \"startDate\": \"30/05/2010\",  \"projectId\": 1,  \"projectName\": \"Project 1\"}";

		// Send project as body to /api/project/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/create")
				.accept(MediaType.APPLICATION_JSON).content(testProject).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	public final void testGetAllProjects() throws Exception {
		when(projectService.getProjects()).thenReturn(Arrays.asList(project1, project2));

		mockMvc.perform(get("/api/project/get")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].projectId", is(1)))
				.andExpect(jsonPath("$[0].projectName", is("Project 1")))
				.andExpect(jsonPath("$[0].startDate", is("30/03/2008"))).andExpect(jsonPath("$[1].projectId", is(2)))
				.andExpect(jsonPath("$[1].projectName", is("Project 2")))
				.andExpect(jsonPath("$[1].startDate", is("30/03/2011"))).andExpect(jsonPath("$[1].priority", is(6)));

		verify(projectService, times(1)).getProjects();
		verifyNoMoreInteractions(projectService);
	}
	

	@Test
	public final void testUpdateProject() throws Exception {
		Mockito.when(projectService.findProjectById(Mockito.eq(1))).thenReturn(project1);
		Mockito.when(projectService.update(Mockito.any(Project.class))).thenReturn(project1);

		// Send project as body to /api/project/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/project/update")
				.accept(MediaType.APPLICATION_JSON).content(testProject).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testUpdateProjectByIdByNull() throws Exception {
		Mockito.when(projectService.findProjectById(Mockito.eq(1))).thenReturn(null);

		// Send project as body to /api/project/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/project/update")
				.accept(MediaType.APPLICATION_JSON).content(testProject).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}		

}
