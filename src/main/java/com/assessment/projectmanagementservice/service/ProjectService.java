/**
 * 
 */
package com.assessment.projectmanagementservice.service;

import java.util.List;

import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.Project;

/**
 * @author manum
 *
 */
public interface ProjectService {

	/**
	 * Add Project
	 * 
	 * @param project
	 * @throws Exception
	 */
	public Project addProject(Project project) throws ProjectManagementException;

	/**
	 * Get Projects
	 * 
	 * @return
	 */
	public List<Project> getProjects();

	/**
	 * Find a Project By Id
	 * 
	 * @param id
	 * @return
	 */
	public Project findProjectById(int id);

	/**
	 * Update a Project
	 * 
	 * @param project
	 * @param id
	 * @return
	 * @throws ProjectManagementException
	 */
	public Project update(Project project) throws ProjectManagementException;	
	
}
