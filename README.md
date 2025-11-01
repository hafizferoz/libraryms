# libraryms
Library Management System in Java and Spring Boot Application.

Key Features:

User Management: Login and logout functionality with automatic user creation. Login will create user if not exists.
Admin Privileges: Only the "admin" user can add books to the library.
Book Operations: Borrow, return, and view  books.

1. borrow > The users can borrow the book if available. The user cannot borrow a book which doesn't exist in system.
2. return > The users can return the borrowed book. The user cannot borrow a book which it has not borrowed.
3. list -> The user can view the list of available and borrowed books.

Waitlist System: Users can join waiting list for borrowed books, and automatically receive books when they become available
Status Tracking: Users can view their borrowed books and waiting list positions.

Fresh Start: Each session execution creates a new environment. The system uses in memory H2 db which will refresh on restart. 
