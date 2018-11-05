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

import com.assessment.projectmanagementservice.model.User;
import com.assessment.projectmanagementservice.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	User user = new User();
	User user1 = new User();

	@Before
	public void setUp() throws Exception {

		user = new User();
		user.setEmployeeId(1234);
		user.setFirstName("First Name");
		user.setLastName("Last Name");
		user.setUserId(1);

		user1 = new User();
		user1.setEmployeeId(4321);
		user1.setFirstName("First Name 1");
		user1.setLastName("Last Name 1");
		user1.setUserId(2);
	}

	private String testUser = "{\"employeeId\": 1234, "
			+ "\"userId\": 1, \"firstName\": \"First Name\", \"lastName\":\"Last Name\"}";

	@Test
	public final void testGetUserById() throws Exception {
		Mockito.when(userService.findUserById(Mockito.eq(1))).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(testUser, result.getResponse().getContentAsString(), false);
	}

	@Test
	public final void testGetUserByIdByNull() throws Exception {
		Mockito.when(userService.findUserById(Mockito.eq(1))).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void userNotFoundTest() throws Exception {
		mockMvc.perform(get("/api/user/6").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public final void testCreateUser() throws Exception {
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(user);
		// Send project as body to /api/user/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/user/create")
				.accept(MediaType.APPLICATION_JSON).content(testUser).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/api/user/1", response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public final void testGetAllUsers() throws Exception {
		when(userService.getUsers()).thenReturn(Arrays.asList(user, user1));

		mockMvc.perform(get("/api/user/get")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].userId", is(1)))
				.andExpect(jsonPath("$[0].firstName", is("First Name"))).andExpect(jsonPath("$[1].userId", is(2)))
				.andExpect(jsonPath("$[1].firstName", is("First Name 1")));

		verify(userService, times(1)).getUsers();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public final void testGetAllUsersByNull() throws Exception {
		when(userService.getUsers()).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/get").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public final void testUpdateUser() throws Exception {
		Mockito.when(userService.findUserById(Mockito.eq(1))).thenReturn(user);
		Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(user);

		// Send project as body to /api/user/update
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/user/update")
				.accept(MediaType.APPLICATION_JSON).content(testUser).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testUpdateUserByIdByNull() throws Exception {
		Mockito.when(userService.findUserById(Mockito.eq(1))).thenReturn(null);

		// Send project as body to /api/user/update
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/user/update")
				.accept(MediaType.APPLICATION_JSON).content(testUser).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public final void testDeleteUser() throws Exception {
		Mockito.when(userService.findUserById(Mockito.eq(1))).thenReturn(user);
		Mockito.doNothing().when(userService).delete(user);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/user/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

	@Test
	public final void testDeleteByIdByNull() throws Exception {
		Mockito.when(userService.findUserById(Mockito.eq(1))).thenReturn(null);

		// Send project as body to /api/user/update
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/user/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

}
