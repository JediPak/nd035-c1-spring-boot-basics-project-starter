package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
// dont use this,
// deletes the setting regarding the catching of the error by Spring MVC,
// this is the reason why the 404 error is being thrown rather than using my own error
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
