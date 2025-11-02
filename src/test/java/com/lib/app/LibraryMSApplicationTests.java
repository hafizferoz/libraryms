package com.lib.app;

import com.lib.cli.LibraryCLI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class LibraryMSApplicationTests {

	@Autowired
	private LibraryCLI cli;
	private ByteArrayOutputStream output;

	@BeforeEach
	void setup() {
		output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
	}

	private String run(String command) {
		output.reset();
		cli.processCmd(command);
		return output.toString();
	}

	@Test
	void contextLoads() {
		//Admin Tests
		Assertions.assertTrue(run("login admin").contains("Hello, admin!\n" +
				"You have access to library management."));
		Assertions.assertTrue(run("list").contains("No books are registered"));
		Assertions.assertTrue(run("add \"The Great Gatsby\"").contains("Book \"The Great Gatsby\" has been added to the library."));
		Assertions.assertTrue(run("add \"1984\"").contains("Book \"1984\" has been added to the library."));
		Assertions.assertTrue(run("logout").contains("Goodbye, admin!"));

		// User Alice Tests
		Assertions.assertTrue(run("login Alice").contains("Hello, Alice!\n" +
				"You don't have any books borrowed yet."));
		Assertions.assertTrue(run("list").contains("1. The Great Gatsby (available)\n" +
				"2. 1984 (available)"));
		Assertions.assertTrue(run("borrow \"1984\"").contains("You borrowed \"1984\"."));
		Assertions.assertTrue(run("list").contains("1. The Great Gatsby (available)\n" +
				"2. 1984 (borrowed)"));
		Assertions.assertTrue(run("status").contains("Your borrowed books:\n" +
				"1. 1984"));
		Assertions.assertTrue(run("logout").contains("Goodbye, Alice!"));

		// User Bob Tests
		Assertions.assertTrue(run("login Bob").contains("Hello, Bob!\n" +
				"You don't have any books borrowed yet."));
		Assertions.assertTrue(run("borrow \"1985\"").contains("Sorry, \"1985\" is not registered."));
		Assertions.assertTrue(run("borrow \"1984\"").contains("Sorry, \"1984\" is currently not available."));
		Assertions.assertTrue(run("waitlist \"1985\"").contains("Sorry, \"1985\" is not registered."));
		Assertions.assertTrue(run("waitlist \"1984\"").contains("You are added to the wait list of \"1984\", your position is 1."));
		Assertions.assertTrue(run("borrow \"The Great Gatsby\"").contains("You borrowed \"The Great Gatsby\"."));
		Assertions.assertTrue(run("status").contains("Your borrowed books:\n" +
				"1. The Great Gatsby\n" +
				"Your wait lists:\n" +
				"1. 1984 - position 1"));
		Assertions.assertTrue(run("logout").contains("Goodbye, Bob!"));

	}

}
