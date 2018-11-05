/**
 * 
 */
package com.assessment.projectmanagementservice.service;

import java.util.List;

import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.User;

/**
 * @author manum
 *
 */
public interface UserService {

	/**
	 * Add User
	 * 
	 * @param user
	 * @throws Exception
	 */
	public User addUser(User user) throws ProjectManagementException;

	/**
	 * Get Users
	 * 
	 * @return
	 */
	public List<User> getUsers();

	/**
	 * Find a User By Id
	 * 
	 * @param id
	 * @return
	 */
	public User findUserById(int id);

	/**
	 * Update a User
	 * 
	 * @param user	
	 * @return
	 * @throws ProjectManagementException
	 */
	public User update(User user) throws ProjectManagementException;

	/**
	 * Delete a user
	 * 
	 * @param user
	 */
	public void delete(User user) throws ProjectManagementException;
	
}
