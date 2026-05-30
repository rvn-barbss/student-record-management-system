# Student Record Management System

A desktop application built with JavaFX and PostgreSQL that allows users to manage student records. This project demonstrates basic database integration and CRUD (Create, Read, Update, Delete) operations using JDBC.

## Features
* **View Records:** Displays all student records dynamically in a TableView.
* **Add Student:** Insert new student records into the database.
* **Update Student:** Select an existing record to edit and save changes.
* **Delete Student:** Remove a student record from the database.
* **Clear Fields:** Quickly reset the input form.

## Technologies Used
* **Frontend:** JavaFX, FXML (SceneBuilder)
* **Backend:** Java
* **Database:** PostgreSQL, JDBC
* **Build Tool:** Maven

## Custom Modifications & Enhancements
This repository includes several improvements and customizations over the standard activity requirements:

1. **Strict Input Validation:** Implemented checks to prevent the submission of empty fields. If a user attempts to add or update a record without filling in all required information, the system halts the database execution and displays a warning dialog.
2. **PUP Santa Rosa Course Dropdown:** To ensure data consistency and prevent typos, the "Course" input was upgraded from a standard text field to a dropdown menu (`ChoiceBox`). It is pre-populated with specific program offerings from the PUP Santa Rosa campus (e.g., BSIT, BSA, BSBA-MM, BSBA-HRM, BSCpE, BSIE, BSECE, BSED-MT, BSED-EN).
3. **Secure Database Credentials:** Instead of hardcoding the PostgreSQL database URL, username, and password directly into the `DBConnection.java` file, the application securely loads these credentials from a local `.env` file. 

## Setup Instructions
1. Clone the repository.
2. Create a PostgreSQL database named `studentdb` and run the following query to create the table:
   ```sql
   CREATE TABLE students (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100),
       course VARCHAR(50),
       year_level VARCHAR(20)
   );
