package com.lib;

import java.util.Scanner;

public class LibraryCLI {
    private Scanner scanner;
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
