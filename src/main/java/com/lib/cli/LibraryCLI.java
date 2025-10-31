package com.lib.cli;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Component
public class LibraryCLI implements Runnable{
    private final Scanner scanner;
    public LibraryCLI() {
        this.scanner = new Scanner(System.in);
    }
    public void run() {
        while (true) {
            System.out.print("Welcome to Library Management System. Please enter login name or create user: \n ");
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
    }
}
