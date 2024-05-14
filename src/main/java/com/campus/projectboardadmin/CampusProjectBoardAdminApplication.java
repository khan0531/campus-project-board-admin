package com.campus.projectboardadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CampusProjectBoardAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusProjectBoardAdminApplication.class, args);
	}

}
