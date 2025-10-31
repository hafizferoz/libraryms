package com.lib.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class H2Database {
    private Connection connection;
    public H2Database(){
        initDB();
    }

    private void initDB() {
        try {
            // Load H2 driver
            Class.forName("org.h2.Driver");
            // Create in-memory database
            connection = DriverManager.getConnection("jdbc:h2:mem:library;DB_CLOSE_DELAY=-1", "sa", "");

            Statement stmt = connection.createStatement();

            // Create users table
            stmt.execute("CREATE TABLE users (" +
                    "name VARCHAR(255) PRIMARY KEY)");

            // Create books table
            stmt.execute("CREATE TABLE books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL," +
                    "is_borrowed BOOLEAN DEFAULT FALSE," +
                    "borrowed_by VARCHAR(255)," +
                    "FOREIGN KEY (borrowed_by) REFERENCES users(name))");

            // Create borrowed_books table
            stmt.execute("CREATE TABLE borrowed_books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_name VARCHAR(255) NOT NULL," +
                    "book_name VARCHAR(255) NOT NULL," +
                    "FOREIGN KEY (user_name) REFERENCES users(name)," +
                    "FOREIGN KEY (book_name) REFERENCES books(name))");

            // Create waitlist table
            stmt.execute("CREATE TABLE waitlist (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "book_name VARCHAR(255) NOT NULL," +
                    "user_name VARCHAR(255) NOT NULL," +
                    "position INT NOT NULL," +
                    "FOREIGN KEY (book_name) REFERENCES books(name)," +
                    "FOREIGN KEY (user_name) REFERENCES users(name))");

            stmt.close();
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
