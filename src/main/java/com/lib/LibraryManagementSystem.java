package com.lib;

import com.lib.cli.LibraryCLI;

// library management main class
public class LibraryManagementSystem {

    LibraryCLI libraryCLI;

    public LibraryManagementSystem() {
        this.libraryCLI = new LibraryCLI();
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.libraryCLI.run();
    }

}
