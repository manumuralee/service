package com.assessment.projectmanagementservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProjectManagementServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void applicationContextLoaded() {
	}

	@Test
	public void applicationContextTest() {
		ProjectManagementServiceApplication.main(new String[] {});
	}

	@Autowired
	private TestRestTemplate restTemplate;
	

	@Test
	public void indexTaskTest() {
		String body = this.restTemplate.getForObject("/api/task/", String.class);
		assertThat(body).isEqualTo("Greetings from Task Manager API services!");
	}

	@Test
	public void indexUserTest() {
		String body = this.restTemplate.getForObject("/api/user/", String.class);
		assertThat(body).isEqualTo("Greetings from User Manager API services!");
	}

	@Test
	public void indexProjectTest() {
		String body = this.restTemplate.getForObject("/api/project/", String.class);
		assertThat(body).isEqualTo("Greetings from Project Manager API services!");
	}

}
