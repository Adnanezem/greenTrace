package com.greentracer.app.dao;

import java.util.List;

public interface Dao<U, T> {
    T getById(U id);

    List<T> getAll();

    Boolean delete(T obj);

    Boolean update(T obj);

    Boolean create(T obj);
}
