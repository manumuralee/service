package com.assessment.projectmanagementservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
public class ProjectServiceImplTest {

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
	private ProjectServiceImpl projectServiceImpl;

	Project project1 = new Project();
	Project project2 = new Project();

	User user = new User();
	Task mockTask = new Task();
	Task mockTask2 = new Task();

	@Before
	public void setUp() throws Exception {

		user = new User();
		user.setEmployeeId(1234);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setUserId(1);

		createMockTask();
		createMockTask1();

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

	@Test
	public final void testAddProject() throws ProjectManagementException {
		project1.setUser(user);
		Mockito.when(userRepository.getOne(Mockito.eq(project1.getUser().getUserId()))).thenReturn(user);
		Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
		Mockito.when(projectRepository.saveAndFlush(project1)).thenReturn(project1);

		Project project = projectServiceImpl.addProject(project1);

		assertEquals(1, project.getProjectId());
		assertEquals("Project 1", project.getProjectName());
		assertEquals(5, project.getPriority());
		assertNotEquals("30/04/2011", project.getStartDate());
	}

	@Test(expected = ProjectManagementException.class)
	public final void testAddProjectException() throws Exception {
		project1.setUser(user);
		Mockito.when(projectRepository.saveAndFlush(project1)).thenThrow(RuntimeException.class);

		@SuppressWarnings("unused")
		Project project = projectServiceImpl.addProject(project1);
	}

	@Test(expected = ProjectManagementException.class)
	public final void testAddDataIntegrityViolationException() throws Exception {
		project1.setUser(user);
		Mockito.when(projectRepository.saveAndFlush(project1)).thenThrow(DataIntegrityViolationException.class);

		@SuppressWarnings("unused")
		Project project = projectServiceImpl.addProject(project1);
	}

	@Test
	public final void testGetProjects() {
		when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

		List<Project> projects = projectServiceImpl.getProjects();

		assertEquals(2, projects.size());
	}

	@Test
	public final void testFindProjectById() {
		Mockito.when(projectRepository.getOne(Mockito.eq(1))).thenReturn(project1);

		Project project = projectServiceImpl.findProjectById(1);

		assertEquals(1, project.getProjectId());
		assertEquals("Project 1", project.getProjectName());
		assertEquals(5, project.getPriority());
		assertNotEquals("30/04/2011", project.getStartDate());
	}

	@Test
	public final void testUpdate() throws ProjectManagementException {
		project1.setUser(user);
		Mockito.when(userRepository.getOne(Mockito.eq(project1.getUser().getUserId()))).thenReturn(user);
		Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
		Mockito.when(projectRepository.saveAndFlush(project1)).thenReturn(project1);

		Project project = projectServiceImpl.update(project1);

		assertEquals(1, project.getProjectId());
		assertEquals("Project 1", project.getProjectName());
		assertEquals(5, project.getPriority());
		assertNotEquals("30/04/2011", project.getStartDate());
	}

	@Test(expected = ProjectManagementException.class)
	public final void testUpdateException() throws Exception {
		project1.setUser(user);
		Mockito.when(projectRepository.saveAndFlush(project1)).thenThrow(RuntimeException.class);

		@SuppressWarnings("unused")
		Project project = projectServiceImpl.update(project1);
	}

	@Test(expected = ProjectManagementException.class)
	public final void testUpdateDataIntegrityViolationException() throws Exception {
		project1.setUser(user);
		Mockito.when(projectRepository.saveAndFlush(project1)).thenThrow(DataIntegrityViolationException.class);

		@SuppressWarnings("unused")
		Project project = projectServiceImpl.update(project1);
	}

}
