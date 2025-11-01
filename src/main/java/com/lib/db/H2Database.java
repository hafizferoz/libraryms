package com.lib.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
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
                    "user_name VARCHAR(255) PRIMARY KEY)");

            // Create books table
            stmt.execute("CREATE TABLE books (" +
                    "book_name VARCHAR(255) PRIMARY KEY," +
                    "is_borrowed BOOLEAN DEFAULT FALSE," +
                    "borrowed_by VARCHAR(255)," +
                    "FOREIGN KEY (borrowed_by) REFERENCES users(user_name))");

            // Create borrowed_books table
            stmt.execute("CREATE TABLE borrowed_books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_name VARCHAR(255) NOT NULL," +
                    "book_name VARCHAR(255) NOT NULL," +
                    "FOREIGN KEY (user_name) REFERENCES users(user_name)," +
                    "FOREIGN KEY (book_name) REFERENCES books(book_name))");

            // Create wait_list table
            stmt.execute("CREATE TABLE wait_list (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "book_name VARCHAR(255) NOT NULL," +
                    "user_name VARCHAR(255) NOT NULL," +
                    "position INT NOT NULL," +
                    "FOREIGN KEY (book_name) REFERENCES books(book_name)," +
                    "FOREIGN KEY (user_name) REFERENCES users(user_name))");

            stmt.close();
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
