package com.ex.model.dao;

import com.ex.model.TicketDetermination;
import java.util.Collections;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.ex.exception.NotImplementedException;

import java.util.List;

public class TicketDeterminationDAO implements DataAccessible<TicketDetermination, Integer> {

    private final SessionFactory sessionFactory;

    public TicketDeterminationDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TicketDetermination create(TicketDetermination ticketDetermination) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        int ticketDeterminationId = (Integer) session.save(ticketDetermination);
        ticketDetermination.setId(ticketDeterminationId);

        tx.commit();
        session.close();

        return ticketDetermination;
    }

    @Override
    public TicketDetermination retrieveOne(Integer id) {
        try {
            throw new NotImplementedException();
        } catch (NotImplementedException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TicketDetermination> retrieveAll() {
        try {
            throw new com.ex.exception.NotImplementedException();
        } catch (com.ex.exception.NotImplementedException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public TicketDetermination update(TicketDetermination obj) {
        try {
            throw new com.ex.exception.NotImplementedException();
        } catch (com.ex.exception.NotImplementedException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public TicketDetermination delete(Integer id) {
        try {
            throw new com.ex.exception.NotImplementedException();
        } catch (com.ex.exception.NotImplementedException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
