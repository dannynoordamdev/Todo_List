This code is a simple Java program that allows the user to add tasks to a PostgreSQL database and retrieve tasks based on a name. The program uses JDBC to connect to the database and execute SQL queries.

When the program is executed, the user is presented with a menu that has three options:

Option 1: Add task - The user is prompted to enter a name and a task. The program then inserts the data into the "tasks" table in the database.
Option 2: Read existing task - The user is prompted to enter a name. The program then retrieves all tasks associated with that name from the "tasks" table in the database and displays them on the screen.
Option 3: Exit - The program exits.
The program first establishes a connection to the database using the JDBC driver. The URL, username, and password for the database are hardcoded in the code. The program then uses prepared statements to execute SQL queries to insert and retrieve data from the "tasks" table in the database.

If there is an error during the execution of the program, the program catches the exception and displays an error message to the user.

This program can be easily extended to add more features, such as updating and deleting tasks, or adding more tables to the database.
