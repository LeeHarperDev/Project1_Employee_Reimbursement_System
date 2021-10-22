# Employee Reimbursement System

## Project Description

This is an Employee Reimbursement System. Using this system, an employee will be able to manage their accounts and open new reimbursement tickets. A manager will be able to manage their account and view all, or a specific employees, reimbursement ticket information.

## Technologies Used

* Java - Version 15
* Maven
* Hibernate - Version 4.3.11
* JUnit - Version 4.11
* AssertJ - Version 3.21
* HSQLDB - Version 2.6.0
* Log4J - Version 1.2.12
* Javalin - Version 4
* PostgreSQL - Version 42.2.23
* Jackson - Version 2.13.0-rc2
* BCrypt - Version 0.9.0
* Java-JWT - Version 3.18.2

## Features

The following list details what features are in the current program, as well as what features would be developed in the future.
* An employee/manager is able to log into and out of an account.
* An employee/manager can view/edit their account information.
* An employee/manager can view their reimbursement tickets as well as create a new one.
* A manager can view a list of all employees.
* A manager can view a collection of all tickets submitted.
* A manager can view all of the reimbursement requests of a single employee.
* A manager can view the account information of any employee.
* A manager can register a new employee to the system.
* All ticket lists can be filtered by their determination, such as PENDING or APPROVED.
* All passwords are securely hashed before being stored into the database, which promotes account security.

To-do list:
* Make the application fully responsive.
* Allow for the user to upload an account image.
* Fine-tune some aspects of the styling, such as implementing a fade-in animation to the modal.
* Implement a mailing system, where users can be notified of the status of their reimbursement ticket.
* Implement an "Owner" role, which will be able to create/manage both employees and managers.
* Implement widespread usage of the authentication guard.
* Implement pagination to the ticket and employee list.

## Getting Started

To get started, first you must navigate to the folder you wish to install this program to. To achieve this, use the following command:
cd <PATH_TO_FOLDER>.
Afterwards, you must clone the repository into that folder or a subfolder. To do this, perform the following command.
git clone https://github.com/LeeHarperDev/Project1_Employee_Reimbursement_System.git <SUBFOLDER OR ./>
After this is done, the repository should be available within the folder. In order for it to work, you must create a "hibernate.cfg.xml" file inside of the "src/main/resources" folder that follows the format of "hibernate.cfg.example.xml".
If the database has not already been set up, please run the SQL code located in the "setup.sql" file. Note that this file contains
After the Hibernate config file is initiated and the database is set up, the project is set up and ready to be ran.

All of these steps should be the same between Windows and Unix.

## Usage

After starting the program, the application can be reached inside of a browser at http://localhost:8080. Upon opening the application, you will be greeted with the following screen.
![image](https://user-images.githubusercontent.com/42632613/138480475-8da1b5ed-f36a-46a6-ada5-f089ae05bc0e.png)
After logging in, you will be greeted with either an employee dashboard or a manager dashboard. From here, you are able to select an action that you are currently authenticated to do. An example of the manager dashboard is shown below, and can be accessed by default by using the credentials username:"admin" and password:"admin".
![image](https://user-images.githubusercontent.com/42632613/138480884-f048ec1e-c58d-4a6a-ac23-eea41f533390.png)
An example of one such action is that a manager can view a collection of all tickets, and select ones to close if they are pending.
![image](https://user-images.githubusercontent.com/42632613/138481367-2bfcf8fe-2289-4c58-9e10-665e548bd898.png)
The current user can log out by clicking the "Logout" button located to the top-right of the application.


## License

This project uses the following license: [MIT License](https://github.com/LeeHarperDev/Project1_Employee_Reimbursement_System/blob/main/LICENSE).

