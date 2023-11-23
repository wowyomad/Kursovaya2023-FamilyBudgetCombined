package com.gnida.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
    List<T> getAll();
    T get(K key);

    T save(T entity);
}
