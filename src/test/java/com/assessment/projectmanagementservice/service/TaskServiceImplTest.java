/**
 * 
 */
package com.assessment.projectmanagementservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
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
import org.mockito.MockitoAnnotations;
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

/**
 * @author manum
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceImplTest {

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
	private TaskServiceImpl taskService;

	Task mockTask = new Task();
	Task mockTask2 = new Task();
	Task mockTask3 = new Task();
	Project project1 = new Project();
	User user = new User();

	@Before
	public void setup() throws ParseException {
		MockitoAnnotations.initMocks(this);

		user = new User();
		user.setEmployeeId(1234);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setUserId(1);

		project1 = new Project();
		project1.setProjectId(1);
		project1.setProjectName("Project 1");
		project1.setStartDate(dateFormat.parse("30/03/2008"));
		project1.setEndDate(dateFormat.parse("30/04/2009"));
		project1.setPriority(5);

		createMockTask();
		createMockTask1();
		createMockTask2();
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
		mockTask.setIsParent(1);
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

	private void createMockTask2() throws ParseException {
		mockTask3.setTaskId(4);
		mockTask3.setTaskName("Test Task 3");
		mockTask3.setStartDate(dateFormat.parse("30/03/2012"));
		mockTask3.setEndDate(dateFormat.parse("30/04/2013"));
		mockTask3.setPriority(1);
		ParentTask parentTask = new ParentTask("Test Parent Task 1");
		parentTask.setParentTaskId(2);
		parentTask.setStatus(1);
		mockTask3.setParentTask(parentTask);
		mockTask3.setStatus(1);
	}

	/**
	 * Test method for
	 * {@link com.casestudy.taskmanagerservice.service.TaskServiceImpl#addTask(com.casestudy.taskmanagerservice.model.Task)}.
	 * 
	 * @throws ParseException
	 * @throws ProjectManagementException
	 */
	@Test
	public final void testAddTask() throws ParseException, ProjectManagementException {

		// Mockito.when(parentTaskRepository.findParentTaskByName(mockTask.getTaskName())).thenReturn(null);
		mockTask.setUser(user);
		mockTask.setProject(project1);
		mockTask.setIsParent(1);
		mockTask.setEndDate(null);	
		ParentTask parentTask = new ParentTask(mockTask.getTaskName());
		Mockito.when(parentTaskRepository.saveAndFlush(parentTask)).thenReturn(parentTask);
		Mockito.when(taskManagerRepository.save(mockTask)).thenReturn(mockTask);
		Task task = taskService.addTask(mockTask);
		assertEquals(2, task.getTaskId());
		assertEquals("Test Task", task.getTaskName());
		assertEquals(5, task.getPriority());
		assertNotEquals("30/04/2008", task.getStartDate());
		assertEquals(null, task.getEndDate());
	}

	@Test(expected = ProjectManagementException.class)
	public final void testAddTaskConstraintViolationException() throws Exception {
		Mockito.when(taskManagerRepository.save(mockTask)).thenThrow(DataIntegrityViolationException.class);

		@SuppressWarnings("unused")
		Task task = taskService.addTask(mockTask);
	}

	@Test(expected = ProjectManagementException.class)
	public final void testAddTaskException() throws Exception {
		Mockito.when(taskManagerRepository.save(mockTask)).thenThrow(RuntimeException.class);

		@SuppressWarnings("unused")
		Task task = taskService.addTask(mockTask);
	}

	/**
	 * Test method for
	 * {@link com.casestudy.taskmanagerservice.service.TaskServiceImpl#getTasks()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public final void testGetTasks() throws ParseException {

		when(taskManagerRepository.findAll()).thenReturn(Arrays.asList(mockTask, mockTask2));
		List<Task> tasks = taskService.getTasks();
		assertEquals(2, tasks.size());
	}

	/**
	 * Test method for
	 * {@link com.casestudy.taskmanagerservice.service.TaskServiceImpl#findTaskById(int)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public final void testFindTaskById() throws ParseException {

		Mockito.when(taskManagerRepository.getOne(Mockito.eq(2))).thenReturn(mockTask);
		Task task = taskService.findTaskById(2);
		assertEquals(2, task.getTaskId());
		assertEquals("Test Task", task.getTaskName());
		assertEquals(5, task.getPriority());
		assertNotEquals("30/04/2008", task.getStartDate());

	}

	/**
	 * Test method for
	 * {@link com.casestudy.taskmanagerservice.service.TaskServiceImpl#update(com.casestudy.taskmanagerservice.model.Task, int)}.
	 * 
	 * @throws ParseException
	 * @throws ProjectManagementException
	 */
	@Test
	public final void testUpdate() throws ParseException, ProjectManagementException {

		mockTask.setUser(user);
		mockTask.setProject(project1);
		mockTask.setIsParent(1);
		ParentTask parentTask = new ParentTask(mockTask.getTaskName());
		parentTask.setParentTaskId(44);
		mockTask.setParentTask(parentTask);		
		Mockito.when(taskManagerRepository.saveAndFlush(mockTask)).thenReturn(mockTask);
		Mockito.when(parentTaskRepository.findParentTaskByTaskId(Mockito.eq(mockTask.getTaskId()))).thenReturn(mockTask.getParentTask());
		Mockito.when(parentTaskRepository.saveAndFlush(mockTask.getParentTask())).thenReturn(mockTask.getParentTask());
		
		
		Task task = taskService.update(mockTask);
		assertEquals(2, task.getTaskId());
		assertEquals("Test Task", task.getTaskName());
		assertEquals(5, task.getPriority());
		assertNotEquals("30/04/2008", task.getStartDate());
	}

	@Test(expected = ProjectManagementException.class)
	public final void testUpdateTaskConstraintViolationException() throws Exception {
		Mockito.when(taskManagerRepository.saveAndFlush(mockTask)).thenThrow(DataIntegrityViolationException.class);

		@SuppressWarnings("unused")
		Task task = taskService.update(mockTask);
	}

	@Test(expected = ProjectManagementException.class)
	public final void testUpdateTaskException() throws Exception {
		Mockito.when(taskManagerRepository.saveAndFlush(mockTask)).thenThrow(RuntimeException.class);

		@SuppressWarnings("unused")
		Task task = taskService.update(mockTask);
	}

	/**
	 * Test method for
	 * {@link com.casestudy.taskmanagerservice.service.TaskServiceImpl#getParentTasks()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public final void testGetParentTasks() throws ParseException {

		when(parentTaskRepository.findAll())
				.thenReturn(Arrays.asList(mockTask.getParentTask(), mockTask2.getParentTask()));
		List<ParentTask> tasks = taskService.getParentTasks();
		assertEquals(2, tasks.size());

	}

	/**
	 * Test method for
	 * {@link com.casestudy.taskmanagerservice.service.TaskServiceImpl#testendTask(com.casestudy.taskmanagerservice.model.Task, int)}.
	 * 
	 * @throws ParseException
	 * @throws ProjectManagementException
	 */
	@Test
	public final void testendTask() throws ParseException, ProjectManagementException {
		
		mockTask3.setIsParent(1);
		mockTask3.setUser(user);
		mockTask3.setProject(project1);
		ParentTask parentTask = new ParentTask(mockTask3.getTaskName());
		parentTask.setParentTaskId(44);
		mockTask3.setParentTask(parentTask);		
		Mockito.when(taskManagerRepository.saveAndFlush(mockTask3)).thenReturn(mockTask3);
		Mockito.when(parentTaskRepository.findParentTaskByTaskId(Mockito.eq(mockTask3.getTaskId()))).thenReturn(mockTask3.getParentTask());
		Mockito.when(parentTaskRepository.saveAndFlush(mockTask3.getParentTask())).thenReturn(mockTask3.getParentTask());

		Task task = taskService.endTask(mockTask3);
		assertEquals(1, task.getStatus());

	}	

}
