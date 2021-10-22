package com.ex.controller.api;

import com.ex.controller.auth.Roles;
import com.ex.model.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import com.ex.model.User;
import com.ex.model.dao.DataAccessible;
import com.ex.model.dao.UserDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserHttpController implements CrudHandler {

    private final SessionFactory sessionFactory;
    private static Logger logger = LogManager.getLogger(TicketHttpController.class);

    public UserHttpController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /***
     *
     * Function: UserHttpController.create(Context context).
     * Purpose: Creates and stores a new User object model to the database, and sends the created user back through
     *          the Javalin Context as a result.
     * Precondition: The body of the context is in a valid JSON format.
     * Postcondition: The created User object model is stored in the database.
     *
     * @param context The Javalin context associated with the request.
     *
     */
    @Override
    public void create(@NotNull Context context) {
        logger.info("Beginning CREATE request.");
        try {
            DataAccessible<User, Integer> userStorage = new UserDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();

            User user = mapper.readValue(context.body(), User.class);

            // Give the user the EMPLOYEE role by default.
            Role role = new Role(Roles.EMPLOYEE.ordinal(), Roles.EMPLOYEE.name());
            user.setRole(role);

            User savedUser = userStorage.create(user);

            String resultString = mapper.writeValueAsString(savedUser);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(201);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with the parsing of the Context body JSON.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: UserHttpController.delete(Context context, String s).
     * Purpose: Deletes a User object model from the database and sends the deleted user back through
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
            DataAccessible<User, Integer> userStorage = new UserDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();

            User deletedUser = userStorage.delete(Integer.valueOf(s));
            String resultString = mapper.writeValueAsString(deletedUser);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the Ticket as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: UserHttpController.getAll(Context context).
     * Purpose: Retrieves a list of all User object models stored in the database, and sends the retrieved list back
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
            DataAccessible<User, Integer> userStorage = new UserDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();

            List<User> users = userStorage.retrieveAll();

            String resultString = mapper.writeValueAsString(users);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the list of Tickets as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: UserHttpController.getOne(Context context, String s).
     * Purpose: Retrieves a User object model from the database and sends the user back through the Javalin Context
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
            DataAccessible<User, Integer> userStorage = new UserDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();
            int userId = Integer.parseInt(s);

            User user = userStorage.retrieveOne(userId);
            String resultString = mapper.writeValueAsString(user);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with processing the list of Tickets as a JSON string.");
            context.status(500);
            e.printStackTrace();
        }
    }

    /***
     *
     * Function: UserHttpController.update(Context context, String s).
     * Purpose: Updates a User object model from the database and sends the updated user back through the Javalin Context
     *          as a result.
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
            DataAccessible<User, Integer> userStorage = new UserDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();
            int userId = Integer.parseInt(s);

            User user = mapper.readValue(context.body(), User.class);
            user.setId(userId);

            User updatedUser = userStorage.update(user);
            String resultString = mapper.writeValueAsString(updatedUser);

            logger.info("Sending result back to the user.");
            context.contentType("application/json");
            context.status(200);
            context.result(resultString);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred with parsing the object as a.");
            context.status(500);
            e.printStackTrace();
        }
    }
}
