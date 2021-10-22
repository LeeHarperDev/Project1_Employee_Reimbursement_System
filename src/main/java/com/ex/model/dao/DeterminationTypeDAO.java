package com.ex.model.dao;

import com.ex.model.DeterminationType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class DeterminationTypeDAO implements DataAccessible<DeterminationType, Integer> {

    private SessionFactory sessionFactory;

    public DeterminationTypeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public DeterminationType create(DeterminationType determinationType) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(determinationType);

        tx.commit();
        session.close();

        return determinationType;
    }

    @Override
    public DeterminationType retrieveOne(Integer id) {
        return null;
    }

    @Override
    public List<DeterminationType> retrieveAll() {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<DeterminationType> determinationTypes = session.createCriteria(DeterminationType.class).addOrder(Order.asc("id")).list();

        tx.commit();
        session.close();

        return determinationTypes;
    }

    @Override
    public DeterminationType update(DeterminationType obj) {
        return null;
    }

    @Override
    public DeterminationType delete(Integer id) {
        return null;
    }
}
