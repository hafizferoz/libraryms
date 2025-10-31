package com.lib.app;

import com.lib.cli.LibraryCLI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lib")
public class LibraryMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryMSApplication.class, args);
		LibraryCLI system = new LibraryCLI();
		system.run();
	}

}
