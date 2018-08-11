package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.model.Task;
import com.example.repository.TaskRepository;

@SpringBootApplication
public class DemoApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner setup(TaskRepository taskRepository) {
		return (args) -> {
			taskRepository.save(new Task("Remove unused imports", true));
			taskRepository.save(new Task("Clean the code", true));
			taskRepository.save(new Task("Build the artifacts", false));
			taskRepository.save(new Task("Deploy the jar file", true));
			logger.info("The sample data has been generated");
		};
	}
}
