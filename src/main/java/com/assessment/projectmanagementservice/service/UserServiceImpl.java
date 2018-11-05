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

import com.assessment.projectmanagementservice.dao.ProjectRepository;
import com.assessment.projectmanagementservice.dao.TaskMangerRepository;
import com.assessment.projectmanagementservice.dao.UserRepository;
import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.Project;
import com.assessment.projectmanagementservice.model.Task;
import com.assessment.projectmanagementservice.model.User;

/**
 * @author manum
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskMangerRepository taskMangerRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.assessment.projectmanagementservice.service.UserService#addUser(com.
	 * assessment.projectmanagementservice.model.User)
	 */
	@Transactional
	@Override
	public User addUser(User user) throws ProjectManagementException {
		try {
			return userRepository.saveAndFlush(user);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				LOGGER.error("UserServiceImpl: update: " + e.getMessage());
				throw new ProjectManagementException("Duplicate User", ERROR_E003_DUPLICATE, "Duplicate User");
			}
			LOGGER.error("UserServiceImpl: delete: " + e.getMessage());
			throw new ProjectManagementException("Failed to update User", ERROR_E006_DELETE_FAILED, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.assessment.projectmanagementservice.service.UserService#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.assessment.projectmanagementservice.service.UserService#findUserById(int)
	 */
	@Override
	public User findUserById(int id) {
		return userRepository.getOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.assessment.projectmanagementservice.service.UserService#update(com.
	 * assessment.projectmanagementservice.model.User, int)
	 */
	@Transactional
	@Override
	public User update(User user) throws ProjectManagementException {
		try {
			return userRepository.saveAndFlush(user);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				LOGGER.error("UserServiceImpl: update: " + e.getMessage());
				throw new ProjectManagementException("Duplicate User", ERROR_E003_DUPLICATE, "Duplicate User");
			}
			LOGGER.error("UserServiceImpl: delete: " + e.getMessage());
			throw new ProjectManagementException("Failed to update User", ERROR_E006_DELETE_FAILED, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.assessment.projectmanagementservice.service.UserService#delete(int)
	 */
	@Override
	public void delete(User user) throws ProjectManagementException {
		try {
			List<Project> projects = projectRepository.findProjectByUserId(user.getUserId());
			for (Project project : projects) {
				project.setUser(null);
				projectRepository.save(project);
			}

			List<Task> tasks = taskMangerRepository.findTaskByUserId(user.getUserId());
			for (Task task : tasks) {
				task.setUser(null);
				taskMangerRepository.save(task);
			}

			//projectRepository.flush();
			//taskMangerRepository.flush();
			userRepository.deleteById(user.getUserId());
		} catch (Exception e) {
			LOGGER.error("UserServiceImpl: delete: " + e.getMessage());
			throw new ProjectManagementException("Failed to update User", ERROR_E006_DELETE_FAILED, e.getMessage());
		}

	}

}
