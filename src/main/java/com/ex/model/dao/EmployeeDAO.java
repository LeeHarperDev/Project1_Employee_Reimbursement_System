package com.ex.model.dao;

import com.ex.model.Employee;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class EmployeeDAO implements DataAccessible<Employee, Integer> {

    private SessionFactory sessionFactory;
    private Logger logger = LogManager.getLogger(EmployeeDAO.class);

    public EmployeeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /***
     *
     * Function: EmployeeDAO.create(Employee employee).
     * Purpose: Stores a new Employee object model to the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: The Employee object model is stored to the database.
     *
     * @param employee The Employee object model being stored.
     * @return The created Employee object model.
     *
     */
    @Override
    public Employee create(Employee employee) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(employee);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return employee;
    }

    /***
     *
     * Function: EmployeeDAO.retrieveOne(Integer id).
     * Purpose: Retrieves an Employee object model from the database based on a given ID.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @param id The ID of the Employee object model being searched for.
     * @return The Employee object model with an associated ID.
     *
     */
    @Override
    public Employee retrieveOne(Integer id) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Employee employee = (Employee) session.get(Employee.class, id);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return employee;
    }

    /***
     *
     * Function: EmployeeDAO.retrieveAll().
     * Purpose: Retrieves a list of all Employee object models stored in the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @return The list of all Employee object models stored in the database.
     *
     */
    @Override
    public List<Employee> retrieveAll() {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<Employee> employees = session.createCriteria(Employee.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.asc("id")).list();

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return employees;
    }

    /***
     *
     * Function: EmployeeDAO.update(Employee employee).
     * Purpose: Updates an Employee object model that is stored in the database.
     * Precondition: A valid database connection has been established.
     * Postcondition: The updated Employee object model is saved to the database.
     *
     * @param employee The Employee object model being updated.
     * @return The updated Employee object model.
     *
     */
    @Override
    public Employee update(Employee employee) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.update(employee);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return employee;
    }

    /***
     *
     * Function: EmployeeDAO.delete(Integer id).
     * Purpose: Deletes an Employee object model from the database based on a given ID.
     * Precondition: A valid database connection has been established.
     * Postcondition: N/A.
     *
     * @param id The ID of the Ticket object model being deleted.
     * @return The deleted Employee object model.
     *
     */
    @Override
    public Employee delete(Integer id) {
        logger.info("Initializing session.");
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Employee employee = (Employee) session.get(Employee.class, id);
        session.delete(employee);

        logger.info("Committing transaction.");
        tx.commit();
        logger.info("Closing session.");
        session.close();

        return employee;
    }
}
