/**
 * 
 */
package com.assessment.projectmanagementservice.service;

import java.util.List;

import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.ParentTask;
import com.assessment.projectmanagementservice.model.Task;

/**
 * @author manum
 *
 */
public interface TaskService {

	/**
	 * Add Task
	 * 
	 * @param task
	 * @throws Exception
	 */
	public Task addTask(Task task) throws ProjectManagementException;

	/**
	 * Get Tasks
	 * 
	 * @return
	 */
	public List<Task> getTasks();

	/**
	 * Find a Task By Id
	 * 
	 * @param id
	 * @return
	 */
	public Task findTaskById(int id);

	/**
	 * Update a Task
	 * 
	 * @param task	 
	 * @return Task
	 * @throws ProjectManagementException
	 */
	public Task update(Task task) throws ProjectManagementException;

	/**
	 * Delete a task
	 * 
	 * @param id
	 * 
	 *            public void delete(int id) throws ProjectManagementException;
	 */

	/**
	 * get Parent Tasks
	 * 
	 * @return List<ParentTask>
	 */
	public List<ParentTask> getParentTasks();

	/**
	 * End a Task
	 * 
	 * @param task
	 * @return Task
	 * @throws ProjectManagementException
	 */
	public Task endTask(Task task) throws ProjectManagementException;	

}
