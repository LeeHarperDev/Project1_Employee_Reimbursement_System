package com.ex.model.dao;

import java.util.List;

public interface DataAccessible<T, I> {
    T create(T obj);
    T retrieveOne(I id);
    List<T> retrieveAll();
    T update(T obj);
    T delete(I id);
}
