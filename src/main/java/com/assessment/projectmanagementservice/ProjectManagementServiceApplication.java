package com.assessment.projectmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProjectManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementServiceApplication.class, args);
	}
}

// For Run as a WAR
// @SpringBootApplication
// @EnableCaching
// public class ProjectManagementServiceApplication extends
// SpringBootServletInitializer {
//
// public static void main(String[] args) {
// SpringApplication.run(ProjectManagementServiceApplication.class, args);
// }
// }

// @Override
// protected SpringApplicationBuilder configure(SpringApplicationBuilder
// application) {
// return application.sources(ProjectManagementServiceApplication.class);
// }
