package com.ex.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ex.controller.auth.AuthenticationController;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import com.ex.model.Ticket;
import com.ex.model.dao.DataAccessible;
import com.ex.model.dao.TicketDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TicketHttpController implements CrudHandler {

    private static Logger logger = LogManager.getLogger(TicketHttpController.class);
    private final SessionFactory sessionFactory;

    public TicketHttpController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /***
     *
     * Function: TicketHttpController.create(Context context).
     * Purpose: Creates and stores a new Ticket object model to the database, and sends the created ticket back through
     *          the Javalin Context as a result.
     * Precondition: The body of the context is in a valid JSON format.
     * Postcondition: The created Ticket object model is stored in the database.
     *
     * @param context The Javalin context associated with the request.
     *
     */
    @Override
    public void create(@NotNull Context context) {
        logger.info("Beginning CREATE request.");
        try {
            AuthenticationController auth = new AuthenticationController();
            String token = context.cookieStore("token");
            DataAccessible<Ticket, Integer> ticketStorage = new TicketDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();

            logger.debug("Context body JSON: " + context.body());

            Ticket ticket = mapper.readValue(context.body(), Ticket.class);
            ticket.setOwner(auth.getUserFromToken(token));
            Ticket savedTicket = ticketStorage.create(ticket);

            String resultString = mapper.writeValueAsString(savedTicket);

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
     * Function: TicketHttpController.delete(Context context, String s).
     * Purpose: Deletes a Ticket object model from the database and sends the deleted ticket back through
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
            DataAccessible<Ticket, Integer> ticketStorage = new TicketDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();
            int ticketId = Integer.parseInt(s);

            Ticket deletedTicket = ticketStorage.delete(ticketId);
            String resultString = mapper.writeValueAsString(deletedTicket);

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
     * Function: TicketHttpController.getAll(Context context).
     * Purpose: Retrieves a list of all Ticket object models stored in the database, and sends the retrieved list back
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
            ObjectMapper mapper = new ObjectMapper();
            DataAccessible<Ticket, Integer> ticketStorage = new TicketDAO(this.sessionFactory);

            List<Ticket> tickets = ticketStorage.retrieveAll();

            String resultString = mapper.writeValueAsString(tickets);

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
     * Function: TicketHttpController.getOne(Context context, String s).
     * Purpose: Retrieves a Ticket object model from the database and sends the ticket back through the Javalin Context
     *          as a result.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     * @param s The ID of the Ticket object model being searched for.
     *
     */
    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        logger.info("Beginning GETONE request.");
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataAccessible<Ticket, Integer> ticketStorage = new TicketDAO(this.sessionFactory);
            int ticketId = Integer.parseInt(s);

            Ticket ticket = ticketStorage.retrieveOne(ticketId);
            String resultString = mapper.writeValueAsString(ticket);

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
     * Function: TicketHttpController.update(Context context, String s).
     * Purpose: Updates a Ticket object model from the database and sends the updated ticket back through the Javalin Context
     *          as a result.
     * Precondition: N/A.
     * Postcondition: N/A.
     *
     * @param context The Javalin context associated with the request.
     * @param s The ID of the Ticket object model being searched for.
     *
     */
    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        logger.info("Beginning UPDATE request.");
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataAccessible<Ticket, Integer> ticketStorage = new TicketDAO(this.sessionFactory);
            int employeeId = Integer.parseInt(s);

            Ticket ticket = mapper.readValue(context.body(), Ticket.class);
            ticket.setId(employeeId);
            logger.debug("Object being updated: " + ticket.toString());

            Ticket updatedTicket = ticketStorage.update(ticket);
            String resultString = mapper.writeValueAsString(updatedTicket);

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
