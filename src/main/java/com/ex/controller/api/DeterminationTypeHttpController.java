package com.ex.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import com.ex.model.DeterminationType;
import com.ex.model.dao.DataAccessible;
import com.ex.model.dao.DeterminationTypeDAO;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import com.ex.exception.NotImplementedException;

import java.util.List;

public class DeterminationTypeHttpController implements CrudHandler {

    private final SessionFactory sessionFactory;

    public DeterminationTypeHttpController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(@NotNull Context context) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataAccessible<DeterminationType, Integer> determinationTypeStorage = new DeterminationTypeDAO(this.sessionFactory);

            DeterminationType determinationType = mapper.readValue(context.body(), DeterminationType.class);

            DeterminationType savedDeterminationType = determinationTypeStorage.create(determinationType);

            String resultString = mapper.writeValueAsString(savedDeterminationType);

            context.contentType("application/json");
            context.result(resultString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        try {
            throw new NotImplementedException();
        } catch (NotImplementedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void getAll(@NotNull Context context) {
        try {
            DataAccessible<DeterminationType, Integer> determinationTypeStorage = new DeterminationTypeDAO(this.sessionFactory);
            ObjectMapper mapper = new ObjectMapper();

            List<DeterminationType> determinationTypes = determinationTypeStorage.retrieveAll();

            String resultString = mapper.writeValueAsString(determinationTypes);

            context.contentType("application/json");
            context.result(resultString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        try {
            throw new NotImplementedException();
        } catch (NotImplementedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        try {
            throw new NotImplementedException();
        } catch (NotImplementedException exception) {
            exception.printStackTrace();
        }
    }
}
