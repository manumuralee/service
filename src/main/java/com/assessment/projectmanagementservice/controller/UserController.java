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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.assessment.projectmanagementservice.exception.ProjectManagementException;
import com.assessment.projectmanagementservice.model.User;
import com.assessment.projectmanagementservice.service.UserService;

/**
 * @author manum
 *
 */

@RestController
@RequestMapping(value = { "/api/user" })
public class UserController {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@GetMapping("/")
	public String index() {
		return "Greetings from User Manager API services!";
	}

	/**
	 * 
	 * @param id
	 * @return ResponseEntity<User>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("id") int id) throws ProjectManagementException, Exception {
		User user = userService.findUserById(id);
		if (user == null || user.getUserId() <= 0) {
			throw new ProjectManagementException("User Not Found", ERROR_E002_NOT_FOUND, "User Not Found");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	/**
	 * Create User
	 * 
	 * @param user
	 * @param ucBuilder
	 * @return ResponseEntity<User>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user, UriComponentsBuilder ucBuilder)
			throws ProjectManagementException, Exception {
		User newUser = userService.addUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(newUser.getUserId()).toUri());
		return new ResponseEntity<User>(newUser, headers, HttpStatus.CREATED);
	}

	/**
	 * getAllUsers
	 * 
	 * @return List<User>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<User> getAllUsers() throws ProjectManagementException, Exception {

		List<User> users = userService.getUsers();
		if (users == null || users.isEmpty()) {
			throw new ProjectManagementException("No User Found", ERROR_E002_NOT_FOUND, "No User Found");
		}
		return users;
	}

	/**
	 * update User
	 * 
	 * @param user
	 * @return ResponseEntity<User>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws ProjectManagementException, Exception {
		User currentUser = userService.findUserById(user.getUserId());
		if (currentUser == null || currentUser.getUserId() <= 0) {
			throw new ProjectManagementException("User Not Found", ERROR_E002_NOT_FOUND, "User Not Found");
		}
		user.setUserId(currentUser.getUserId());
		User updatedUser = userService.update(user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	/**
	 * delete User
	 * 
	 * @param id
	 * @return ResponseEntity<String>
	 * @throws ProjectManagementException
	 * @throws Exception
	 */
	@DeleteMapping(value = "/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id) throws ProjectManagementException, Exception {
		User user = userService.findUserById(id);
		if (user == null) {
			throw new ProjectManagementException("User Not Found", ERROR_E002_NOT_FOUND, "User Not Found");
		}
		userService.delete(user);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
