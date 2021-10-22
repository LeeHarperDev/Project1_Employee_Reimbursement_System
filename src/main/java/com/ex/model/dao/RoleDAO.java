package com.ex.model.dao;

import com.ex.model.Role;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDAO implements DataAccessible<Role, Integer> {

    private static Logger logger = LogManager.getLogger(RoleDAO.class);
    private SessionFactory sessionFactory;

    public RoleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role create(Role role) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(role);

        tx.commit();
        session.close();

        return role;
    }

    @Override
    public Role retrieveOne(Integer id) {
        return null;
    }

    @Override
    public List<Role> retrieveAll() {
        return null;
    }

    @Override
    public Role update(Role obj) {
        return null;
    }

    @Override
    public Role delete(Integer id) {
        return null;
    }
}
