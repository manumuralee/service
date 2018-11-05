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
import com.assessment.projectmanagementservice.model.Project;
import com.assessment.projectmanagementservice.service.ProjectService;

/**
 * @author manum
 *
 */

@RestController
@RequestMapping(value = { "/api/project" })
public class ProjectController {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	@GetMapping("/")
	public String index() {
		return "Greetings from Project Manager API services!";
	}

	/**
	 * 
	 * @param id
	 * @return ResponseEntity<Project>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> getProjectById(@PathVariable("id") int id) throws ProjectManagementException, Exception {
		Project project = projectService.findProjectById(id);
		if (project == null || project.getProjectId() <= 0) {
			throw new ProjectManagementException("Project Not Found", ERROR_E002_NOT_FOUND, "Project Not Found");
		}
		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}

	/**
	 * Create Project
	 * 
	 * @param project
	 * @param ucBuilder
	 * @return ResponseEntity<Project>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<Project> createProject(@Valid @RequestBody Project project, UriComponentsBuilder ucBuilder)
			throws ProjectManagementException, Exception {
		Project newProject = projectService.addProject(project);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/project/{id}").buildAndExpand(newProject.getProjectId()).toUri());
		return new ResponseEntity<Project>(newProject, headers, HttpStatus.CREATED);
	}

	/**
	 * getAllProjects
	 * 
	 * @return List<Project>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<Project> getAllProjects() throws ProjectManagementException, Exception {
		List<Project> projects = projectService.getProjects();		
		return projects;
	}

	/**
	 * update Project
	 * 
	 * @param project
	 * @return ResponseEntity<Project>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) throws ProjectManagementException, Exception {
		Project currentProject = projectService.findProjectById(project.getProjectId());
		if (currentProject == null || currentProject.getProjectId() <= 0) {
			throw new ProjectManagementException("Project Not Found", ERROR_E002_NOT_FOUND, "Project Not Found");
		}
		project.setProjectId(currentProject.getProjectId());
		Project updatedProject = projectService.update(project);
		return new ResponseEntity<Project>(updatedProject, HttpStatus.OK);
	}

}
