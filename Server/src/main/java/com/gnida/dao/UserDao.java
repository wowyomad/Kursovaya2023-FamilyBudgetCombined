package com.gnida.dao;

import com.gnida.DatabaseUtil;
import com.gnida.entity.UserEntity;

import java.util.List;

public class UserDao implements GenericDao<UserEntity, Integer> {
    @Override
    public List<UserEntity> getAll() {
        try (var session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            var list = session.createQuery("FROM UserEntity", UserEntity.class)
                    .getResultList();
            session.getTransaction().commit();
            return list;
        }
    }
    @Override
    public UserEntity get(Integer key) {
        try(var session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            var user = session.find(UserEntity.class, key);
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public UserEntity save(UserEntity entity) {
        try(var session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public UserEntity update(UserEntity entity) {
        try(var session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public void delete(UserEntity entity) {
        try(var session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();

        }
    }
}
