package com.ex.model.dao;

import com.ex.model.Ticket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class TicketDAO implements DataAccessible<Ticket, Integer> {

    private SessionFactory sessionFactory;
    private Logger logger = LogManager.getLogger(TicketDAO.class);

    public TicketDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /***
     *
     * Function: TicketDAO.create(Ticket ticket).
     * Purpose: Stores a new Ticket object model to the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: The Ticket object model is stored to the database.
     *
     * @param ticket The Ticket object model being stored.
     * @return The created Ticket object model.
     *
     */
    @Override
    public Ticket create(Ticket ticket) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        logger.info("Saving Ticket.");
        logger.debug("Ticket being saved: " + ticket.toString());
        session.save(ticket);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return ticket;
    }

    /***
     *
     * Function: TicketDAO.retrieveOne(Integer id).
     * Purpose: Retrieves a Ticket object model from the database based on a given ID.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @param id The ID of the Ticket object model being searched for.
     * @return The Ticket object model with an associated ID.
     *
     */
    @Override
    public Ticket retrieveOne(Integer id) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Ticket ticket = (Ticket) session.get(Ticket.class, id);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return ticket;
    }


    /***
     *
     * Function: TicketDAO.retrieveAll().
     * Purpose: Retrieves a list of all Ticket object models stored in the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @return The list of all Ticket object models stored in the database.
     *
     */
    @Override
    public List<Ticket> retrieveAll() {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<Ticket> tickets = session.createCriteria(Ticket.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.asc("id")).list();

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return tickets;
    }

    /***
     *
     * Function: TicketDAO.update(Ticket ticket).
     * Purpose: Updates a Ticket object model that is stored in the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: The updated Ticket object model is saved to the database.
     *
     * @param ticket The Ticket object model being updated.
     * @return The updated Ticket object model.
     *
     */
    @Override
    public Ticket update(Ticket ticket) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(ticket);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return ticket;
    }

    /***
     *
     * Function: TicketDAO.delete(Integer id).
     * Purpose: Deletes a Ticket object model from the database based on a given ID.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @param id The ID of the Ticket object model being deleted.
     * @return The deleted Ticket object model.
     *
     */
    @Override
    public Ticket delete(Integer id) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Ticket ticket = (Ticket) session.get(Ticket.class, id);
        session.delete(ticket);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return ticket;
    }
}
