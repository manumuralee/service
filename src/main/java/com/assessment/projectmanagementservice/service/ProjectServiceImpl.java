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

import com.assessment.projectmanagementservice.dao.ProjectRepository;
import com.assessment.projectmanagementservice.dao.TaskMangerRepository;
import com.assessment.projectmanagementservice.dao.UserRepository;
import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.Project;

/**
 * @author manum
 *
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskMangerRepository taskMangerRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.assessment.projectmanagementservice.service.ProjectService#saveAndFlush(
	 * int)
	 */
	@Override
	@Transactional
	public Project addProject(Project project) throws ProjectManagementException {
		try {
			Project newProject = projectRepository.saveAndFlush(project);
			return newProject;
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				LOGGER.error("ProjectServiceImpl: update: " + e.getMessage());
				throw new ProjectManagementException("Duplicate Project", ERROR_E003_DUPLICATE, "Duplicate Project");
			}
			LOGGER.error("ProjectServiceImpl: delete: " + e.getMessage());
			throw new ProjectManagementException("Failed to update Project", ERROR_E006_DELETE_FAILED, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.assessment.projectmanagementservice.service.ProjectService#getProjects(
	 * int)
	 */
	@Override
	public List<Project> getProjects() {
		List<Project> projectList = projectRepository.findAll();
		for (Project project : projectList) {
			project.setTaskCount(taskMangerRepository.findTaskCountByProjectId(project.getProjectId()));
			project.setClosedTaskCount(taskMangerRepository.findClosedTaskCountByProjectId(project.getProjectId()));
		}
		return projectList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.assessment.projectmanagementservice.service.ProjectService#
	 * findProjectById(int)
	 */
	@Override
	public Project findProjectById(int id) {
		return projectRepository.getOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.assessment.projectmanagementservice.service.ProjectService#delete(int)
	 */
	@Override
	@Transactional
	public Project update(Project project) throws ProjectManagementException {
		try {
			Project newProject = projectRepository.saveAndFlush(project);
			return newProject;
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				LOGGER.error("ProjectServiceImpl: update: " + e.getMessage());
				throw new ProjectManagementException("Duplicate Project", ERROR_E003_DUPLICATE, "Duplicate Project");
			}
			LOGGER.error("ProjectServiceImpl: delete: " + e.getMessage());
			throw new ProjectManagementException("Failed to update Project", ERROR_E006_DELETE_FAILED, e.getMessage());
		}
	}

}
