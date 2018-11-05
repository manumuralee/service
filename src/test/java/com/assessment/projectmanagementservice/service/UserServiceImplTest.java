package com.assessment.projectmanagementservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assessment.projectmanagementservice.dao.ParentTaskRepository;
import com.assessment.projectmanagementservice.dao.ProjectRepository;
import com.assessment.projectmanagementservice.dao.TaskMangerRepository;
import com.assessment.projectmanagementservice.dao.UserRepository;
import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.ParentTask;
import com.assessment.projectmanagementservice.model.Project;
import com.assessment.projectmanagementservice.model.Task;
import com.assessment.projectmanagementservice.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Mock
	TaskMangerRepository taskManagerRepository;

	@Mock
	ParentTaskRepository parentTaskRepository;

	@Mock
	ProjectRepository projectRepository;

	@Mock
	UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	User user = new User();
	User user1 = new User();
	Project project1 = new Project();
	Task mockTask = new Task();

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

		createMockTask();

		project1 = new Project();
		project1.setProjectId(1);
		project1.setProjectName("Project 1");
		project1.setStartDate(dateFormat.parse("30/03/2008"));
		project1.setEndDate(dateFormat.parse("30/04/2009"));
		project1.setPriority(5);
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

	@Test
	public final void testAddUser() throws ProjectManagementException {
		Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
		User newUser = userServiceImpl.addUser(user);

		assertEquals(1, newUser.getUserId());
		assertEquals("First Name", newUser.getFirstName());
		assertNotEquals("2323", newUser.getEmployeeId());
	}

	@Test(expected = ProjectManagementException.class)
	public final void testAddUserDataIntegrityViolationException() throws Exception {
		Mockito.when(userRepository.saveAndFlush(user)).thenThrow(DataIntegrityViolationException.class);

		@SuppressWarnings("unused")
		User newUser = userServiceImpl.addUser(user);
	}

	@Test(expected = ProjectManagementException.class)
	public final void testAddUserException() throws Exception {
		Mockito.when(userRepository.saveAndFlush(user)).thenThrow(RuntimeException.class);

		@SuppressWarnings("unused")
		User newUser = userServiceImpl.addUser(user);
	}

	@Test
	public final void testGetUsers() {
		when(userRepository.findAll()).thenReturn(Arrays.asList(user, user1));

		List<User> users = userServiceImpl.getUsers();

		assertEquals(2, users.size());
	}

	@Test
	public final void testFindUserById() {
		Mockito.when(userRepository.getOne(Mockito.eq(1))).thenReturn(user);

		User newUser = userServiceImpl.findUserById(1);

		assertEquals(1, newUser.getUserId());
		assertEquals("First Name", newUser.getFirstName());
		assertNotEquals("1111", newUser.getEmployeeId());

	}

	@Test
	public final void testUpdate() throws ProjectManagementException {
		Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
		User newUser = userServiceImpl.update(user);

		assertEquals(1, newUser.getUserId());
		assertEquals("First Name", newUser.getFirstName());
		assertNotEquals("2324", newUser.getEmployeeId());
	}

	@Test(expected = ProjectManagementException.class)
	public final void testUpdateUserDataIntegrityViolationException() throws Exception {
		Mockito.when(userRepository.saveAndFlush(user)).thenThrow(DataIntegrityViolationException.class);

		@SuppressWarnings("unused")
		User newUser = userServiceImpl.update(user);
	}

	@Test(expected = ProjectManagementException.class)
	public final void testUpdateUserException() throws Exception {
		Mockito.when(userRepository.saveAndFlush(user)).thenThrow(RuntimeException.class);

		@SuppressWarnings("unused")
		User newUser = userServiceImpl.update(user);
	}

	@Test
	public final void testDelete() throws ProjectManagementException {		
		
		Mockito.when(projectRepository.findProjectByUserId(Mockito.eq(project1.getProjectId()))).thenReturn(Arrays.asList(project1));
		Mockito.when(taskManagerRepository.findTaskByUserId(Mockito.eq(mockTask.getTaskId()))).thenReturn(Arrays.asList(mockTask));
		Mockito.when(taskManagerRepository.save(mockTask)).thenReturn(mockTask);
		Mockito.when(projectRepository.save(project1)).thenReturn(project1);
		Mockito.doNothing().when(userRepository).delete(Mockito.any(User.class));
		userServiceImpl.delete(user);
		verify(userRepository, times(1)).deleteById(user.getUserId());;
	}

	@Test(expected = ProjectManagementException.class)
	public final void testDeleteException() throws Exception {
		user.setProject(project1);
		Mockito.when(projectRepository.findProjectByUserId(Mockito.eq(user.getProject().getProjectId()))).thenReturn(null);
		Mockito.when(taskManagerRepository.findTaskByUserId(Mockito.eq(mockTask.getTaskId()))).thenReturn(Arrays.asList(mockTask));
		Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);

		userServiceImpl.delete(user);
	}

}
