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


# Implementation Details:

Tech Stack: Java, Springboot and H2 DB (in memory)

Waiting list Logic:
When a book is returned, the first person in the waiting list automatically gets it and 
Waiting list positions are dynamically updated. While changes are pushed in DB as batch, however for real world scenarios cacheing mechanism can be utilized for better performance.
Users are notified on login if they received a book from their waiting list. The status is 

Command Parsing: Booth quoted and unquoted book names are handled.

Application Start:

LibraryMSApplication: The spring boot main application runs a cli thread to interact via command prompt.

start.bat: Windows batch file which will complie and run the application.
start.sh: Unix/Linux Bash script that compiles and runs the application.


To start on Unix/Linux:
./start.sh

To start on windows:
start.bat