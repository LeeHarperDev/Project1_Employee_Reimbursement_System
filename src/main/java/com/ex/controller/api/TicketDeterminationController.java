package com.ex.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ex.controller.auth.AuthenticationController;
import com.ex.controller.auth.AuthorizationController;
import com.ex.controller.auth.Roles;
import io.javalin.http.Context;
import com.ex.model.Ticket;
import com.ex.model.TicketDetermination;
import com.ex.model.User;
import com.ex.model.dao.DataAccessible;
import com.ex.model.dao.TicketDAO;
import com.ex.model.dao.TicketDeterminationDAO;
import org.hibernate.SessionFactory;

public class TicketDeterminationController {

    private SessionFactory sessionFactory;

    public TicketDeterminationController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void makeDetermination(Context context, String s) {
        try {
            AuthorizationController authorization = new AuthorizationController(Roles.MANAGER);
            AuthenticationController auth = new AuthenticationController();

            String token = context.cookieStore("token");
            User loggedInUser = auth.getUserFromToken(token);

            if (authorization.checkIfAuthorized(loggedInUser)) {
                ObjectMapper mapper = new ObjectMapper();
                DataAccessible<Ticket, Integer> ticketStorage = new TicketDAO(this.sessionFactory);
                DataAccessible<TicketDetermination, Integer> determinationStorage = new TicketDeterminationDAO(this.sessionFactory);

                TicketDetermination determination = mapper.readValue(context.body(), TicketDetermination.class);
                determination.setManager(loggedInUser);

                TicketDetermination fullDetermination = determinationStorage.create(determination);
                Ticket ticket = ticketStorage.retrieveOne(Integer.parseInt(s));
                ticket.setDetermination(determination);
                ticketStorage.update(ticket);

                context.status(200);
                context.result(mapper.writeValueAsString(fullDetermination));
            } else {
                context.status(403);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
