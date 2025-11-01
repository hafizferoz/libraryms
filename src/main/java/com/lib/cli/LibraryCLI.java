package com.lib.cli;

import com.lib.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LibraryCLI implements Runnable{
    private final Scanner scanner;
    @Autowired
    private LibraryService libraryService;
    public LibraryCLI() {
        this.scanner = new Scanner(System.in);
    }
    public void run() {
        System.out.print("Welcome to Library Management System. \n Please enter login name or create user: \n ");
        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            processCmd(input);
        }
    }

    private void processCmd(String input) {

        String[] parts = input.split(" ", 2);
        String cmd = parts[0];
        String arg = parts.length > 1 ? parts[1].replaceAll("^\"|\"$", "") : null;

        switch (cmd) {
            case "login": libraryService.login(arg); break;
            case "logout": libraryService.logout(); break;
            case "add": libraryService.addBook(arg); break;
            case "list": libraryService.listBooks(); break;
            case "borrow": libraryService.borrow(arg); break;
            case "return": libraryService.returnBook(arg); break;
            case "waitlist": libraryService.waitlist(arg); break;
            case "status": libraryService.status(); break;
            default: System.out.println("Unknown command: " + cmd);
        }

    }
}
