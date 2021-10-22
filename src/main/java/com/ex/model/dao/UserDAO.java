package com.ex.model.dao;

import com.ex.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import com.ex.services.PasswordHash;

import java.util.List;

public class UserDAO implements DataAccessible<User, Integer> {

    private final SessionFactory sessionFactory;
    private Logger logger = LogManager.getLogger(UserDAO.class);

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /***
     *
     * Function: UserDAO.create(User user).
     * Purpose: Stores a new User object model to the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: The User object model is stored to the database.
     *
     * @param user The User object model being stored.
     * @return The created User object model.
     *
     */
    @Override
    public User create(User user) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        // Hashes the password for the user.
        user.setPassword(PasswordHash.hashPassword(user.getPassword()));
        session.save(user.getEmployee().getAddress());
        session.save(user.getEmployee());
        session.save(user);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return user;
    }

    /***
     *
     * Function: UserDAO.retrieveOne(Integer id).
     * Purpose: Retrieves a User object model from the database based on a given ID.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @param id The ID of the User object model being searched for.
     * @return The User object model with an associated ID.
     *
     */
    @Override
    public User retrieveOne(Integer id) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        User user = (User) session.get(User.class, id);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return user;
    }

    /***
     *
     * Function: UserDAO.retrieveAll().
     * Purpose: Retrieves a list of all User object models stored in the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @return The list of all User object models stored in the database.
     *
     */
    @Override
    public List<User> retrieveAll() {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<User> users = session.createCriteria(User.class).addOrder(Order.asc("id")).list();

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return users;
    }

    /***
     *
     * Function: UserDAO.update(User user).
     * Purpose: Updates a User object model that is stored in the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: The updated User object model is saved to the database.
     *
     * @param user The User object model being updated.
     * @return The updated User object model.
     *
     */
    @Override
    public User update(User user) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(user);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return user;
    }

    /***
     *
     * Function: UserDAO.delete(Integer id).
     * Purpose: Deletes a User object model from the database based on a given ID.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @param id The ID of the User object model being deleted.
     * @return The deleted User object model.
     *
     */
    @Override
    public User delete(Integer id) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        User user = (User) session.get(User.class, id);
        session.delete(user);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return user;
    }
}
