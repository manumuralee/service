/**
 * 
 */
package com.assessment.projectmanagementservice.controller;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E002_NOT_FOUND;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.ParentTask;
import com.assessment.projectmanagementservice.model.Task;
import com.assessment.projectmanagementservice.service.TaskService;

/**
 * @author Admin
 *
 */
// @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
@RestController
@RequestMapping(value = { "/api/task" })
public class TaskController {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	TaskService taskService;

	@GetMapping("/")
	public String index() {
		return "Greetings from Task Manager API services!";
	}

	/**
	 * 
	 * @param id
	 * @return ResponseEntity<Task>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> getTaskById(@PathVariable("id") int id) throws ProjectManagementException, Exception {
		Task task = taskService.findTaskById(id);
		if (task == null || task.getTaskId() <= 0) {
			throw new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found");
		}
		return new ResponseEntity<Task>(task, HttpStatus.OK);

	}

	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, UriComponentsBuilder ucBuilder)
			throws ProjectManagementException, Exception {

		Task newTask = taskService.addTask(task);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/task/{id}").buildAndExpand(newTask.getTaskId()).toUri());
		return new ResponseEntity<Task>(newTask, headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<Task> getAllTasks() throws ProjectManagementException, Exception {
		List<Task> tasks = taskService.getTasks();		
		return tasks;
	}

	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) throws ProjectManagementException, Exception {
		Task currenttask = taskService.findTaskById(task.getTaskId());
		if (currenttask == null) {
			throw new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found");
		}
		Task updatedTask = taskService.update(task);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}
	

	@GetMapping(value = "/getParents", headers = "Accept=application/json")
	public List<ParentTask> getAllParents() throws ProjectManagementException, Exception {

		List<ParentTask> tasks = taskService.getParentTasks();
		if (tasks == null) {
			throw new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found");
		}
		return tasks;
	}

	@PutMapping(value = "/endTask/{id}", headers = "Accept=application/json")
	public ResponseEntity<Task> endTask(@PathVariable Integer id) throws ProjectManagementException, Exception {
		Task currenttask = taskService.findTaskById(id);
		if (currenttask == null) {
			throw new ProjectManagementException("Task Not Found", ERROR_E002_NOT_FOUND, "Task Not Found");
		}
		Task updatedTask = taskService.endTask(currenttask);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}
}
