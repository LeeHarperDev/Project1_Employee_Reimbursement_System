package com.ex.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ex.controller.auth.AuthenticationController;
import com.ex.exception.NotLoggedInException;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import com.ex.model.Employee;
import com.ex.model.User;
import com.ex.model.dao.DataAccessible;
import com.ex.model.dao.EmployeeDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmployeeHttpController implements CrudHandler {

    private final SessionFactory sessionFactory;
    private static Logger logger = LogManager.getLogger(EmployeeHttpController.class);

    public EmployeeHttpController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /***
     *
     * Function: EmployeeHttpController.create(Context context).
     * Purpose: Creates and stores a new Employee object model to the database, and sends the created employee back through
     *          the Javalin Context as a result.
     * Precondition: The body of the context is in a valid JSON format.
     * Postcondition: The created Employee object model is stored in the database.
     *
     * @param context The Javalin context associated with the request.
     *
     */
    @Override
    public void create(@NotNull Context context) {
        logger.info("Beginning CREATE request.");
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataAccessible<Employee, Integer> employeeStorage = new EmployeeDAO(this.sessionFactory);
            AuthenticationController auth = new AuthenticationController();
            String token = context.cookieStore("token");

            logger.info("Checking to make sure user is logged in.");
            if (auth.getUserFromToken(token) == null) {
                throw new NotLoggedInException();
            }

            logger.debug("Context body JSON: " + context.body());

            Employee employee = mapper.readValue(context.body(), Employee.class);

            Employee savedEmployee = employeeStorage.create(employee);

            String resultString = mapper.writeValueAsString(savedEmployee);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(201);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Employee as a JSON string.");
            context.status(500);
            e.printStackTrace();
        } catch (NotLoggedInException e) {
            logger.error("A create request was made, but not by an authenticated user.");
            context.status(401);
        }
    }

    /***
     *
     * Function: EmployeeHttpController.delete(Context context, String s).
     * Purpose: Deletes an Employee object model from the database and sends the deleted employee back through
     *          the Javalin Context as a result.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     * @param s The ID of the object model being deleted.
     *
     */
    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        logger.info("Beginning DELETE request.");
        try {
            DataAccessible<Employee, Integer> employeeStorage = new EmployeeDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();
            int employeeId = Integer.parseInt(s);

            Employee deletedEmployee = employeeStorage.delete(employeeId);

            String resultString = mapper.writeValueAsString(deletedEmployee);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Employee as a JSON string.");
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: EmployeeHttpController.getAll(Context context).
     * Purpose: Retrieves a list of all Employee object models stored in the database, and sends the retrieved list back
     *          through the Javalin Context as a result.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     *
     */
    @Override
    public void getAll(@NotNull Context context) {
        logger.info("Beginning GETALL request.");
        try {
            DataAccessible<Employee, Integer> employeeStorage = new EmployeeDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();

            List<Employee> employees = employeeStorage.retrieveAll();

            String resultString = mapper.writeValueAsString(employees);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Employee as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: EmployeeHttpController.getOne(Context context, String s).
     * Purpose: Retrieves an Employee object model from the database and sends the employee back through the Javalin Context
     *          as a result.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     * @param s The ID of the User object model being searched for.
     *
     */
    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        logger.info("Beginning GETONE request.");
        try {
            DataAccessible<Employee, Integer> employeeStorage = new EmployeeDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();
            int employeeId = Integer.parseInt(s);

            Employee employee = employeeStorage.retrieveOne(employeeId);
            String resultString = mapper.writeValueAsString(employee);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Employee as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: EmployeeHttpController.getOne(Context context, String s).
     * Purpose: Updates an Employee object model from the database and sends the updated ticket back through the Javalin
     *          Context as a result.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     * @param s The ID of the User object model being searched for.
     *
     */
    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        logger.info("Beginning UPDATE request.");
        try {
            DataAccessible<Employee, Integer> employeeStorage = new EmployeeDAO(this.sessionFactory);
            int employeeId = Integer.parseInt(s);

            ObjectMapper mapper = new ObjectMapper();

            Employee employee = mapper.readValue(context.body(), Employee.class);
            employee.setId(employeeId);

            logger.debug("Object being updated: " + employee.toString());

            Employee updatedEmployee = employeeStorage.update(employee);

            String resultString = mapper.writeValueAsString(updatedEmployee);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Employee as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: EmployeeHttpController.getAllTickets(Context context, String s).
     * Purpose: Retrieves a collection of all tickets submitted by a specific employee.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     * @param s The ID of the object model being deleted.
     *
     */
    public void getAllTickets(Context context, String s) {
        logger.info("Initiating GETALLTICKETS request.");
        try {
            DataAccessible<Employee, Integer> employeeStorage = new EmployeeDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();
            int employeeId = Integer.parseInt(s);

            Employee employee = employeeStorage.retrieveOne(employeeId);
            User employeeUser = employee.getUser();
            String resultString = mapper.writeValueAsString(employeeUser.getTickets());

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Employee as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }
}
