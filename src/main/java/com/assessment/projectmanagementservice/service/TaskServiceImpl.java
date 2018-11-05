/**
 * 
 */
package com.assessment.projectmanagementservice.service;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E003_DUPLICATE;
import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E006_DELETE_FAILED;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.projectmanagementservice.dao.ParentTaskRepository;
import com.assessment.projectmanagementservice.dao.ProjectRepository;
import com.assessment.projectmanagementservice.dao.TaskMangerRepository;
import com.assessment.projectmanagementservice.dao.UserRepository;
import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.ParentTask;
import com.assessment.projectmanagementservice.model.Task;

/**
 * @author Admin
 *
 */
@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskMangerRepository taskManagerRepository;

	@Autowired
	ParentTaskRepository parentTaskRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.casestudy.taskmanagerservice.service.TaskService#addTask(com.casestudy.
	 * taskmanagerservice.model.Task)
	 */
	@Override
	@Transactional
	public Task addTask(Task task) throws ProjectManagementException {
		try {
			Task newTask = taskManagerRepository.save(task);
			if (task.getIsParent() == 1) {
				ParentTask parentTask = new ParentTask(task.getTaskName());
				parentTask.setTask(newTask);
				parentTaskRepository.saveAndFlush(parentTask);
			}
			return newTask;
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				LOGGER.error("TaskServiceImpl: addTask: " + e.getMessage());
				throw new ProjectManagementException("Duplicate Task", ERROR_E003_DUPLICATE, "Duplicate Task");
			}
			LOGGER.error("TaskServiceImpl: addTask: " + e.getMessage());
			throw new ProjectManagementException("Failed to update Task", ERROR_E006_DELETE_FAILED, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casestudy.taskmanagerservice.service.TaskService#getTasks()
	 */
	@Override
	public List<Task> getTasks() {
		return taskManagerRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.casestudy.taskmanagerservice.service.TaskService#findTaskById(int)
	 */
	@Override
	@Transactional
	public Task findTaskById(int id) {
		return taskManagerRepository.getOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.casestudy.taskmanagerservice.service.TaskService#update(com.casestudy.
	 * taskmanagerservice.model.Task, int)
	 */
	@Override
	@Transactional
	public Task update(Task task) throws ProjectManagementException {
		try {
			Task newTask = taskManagerRepository.saveAndFlush(task);
			if (task.getIsParent() == 1) {
				ParentTask parentTask = parentTaskRepository.findParentTaskByTaskId(newTask.getTaskId());
				parentTask.setParentName(newTask.getTaskName());
				parentTaskRepository.saveAndFlush(parentTask);
			}
			return newTask;
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				LOGGER.error("TaskServiceImpl: addTask: " + e.getMessage());
				throw new ProjectManagementException("Duplicate Task", ERROR_E003_DUPLICATE, "Duplicate Task");
			}
			LOGGER.error("TaskServiceImpl: addTask: " + e.getMessage());
			throw new ProjectManagementException("Failed to update Task", ERROR_E006_DELETE_FAILED, e.getMessage());
		}
	}

	@Override
	public List<ParentTask> getParentTasks() {
		return parentTaskRepository.findAll();
	}

	@Override
	@Transactional
	public Task endTask(Task task) throws ProjectManagementException {
		task.setStatus(1);
		Task newTask = taskManagerRepository.saveAndFlush(task);
		if (task.getIsParent() == 1) {
			ParentTask pcurrentTask = parentTaskRepository.findParentTaskByTaskId(task.getTaskId());
			pcurrentTask.setStatus(1);
			parentTaskRepository.saveAndFlush(pcurrentTask);
		}
		return newTask;
	}	

}
