package com.lib.app;

import com.lib.cli.LibraryCLI;
import com.lib.service.LibraryService;
import com.lib.service.LibraryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.lib")
@EnableJpaRepositories(basePackages = "com.lib.repo")
@EntityScan(basePackages = "com.lib.model")
public class LibraryMSApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(LibraryMSApplication.class, args);
		LibraryCLI system = applicationContext.getBean(LibraryCLI.class);
		system.run();
	}

}
