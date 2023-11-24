package com.gnida.dao;


import com.gnida.DatabaseUtil;
import com.gnida.entity.UserEntity;
import com.gnida.entity.UserInfoEntity;
import org.hibernate.Session;

import java.util.List;


public class UserInfoDao implements GenericDao<UserInfoEntity, UserEntity> {

    @Override
    public List<UserInfoEntity> getAll() {
        try (Session session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            var list = session.createSelectionQuery("FROM UserInfoEntity", UserInfoEntity.class).list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public UserInfoEntity get(UserEntity key) {
        try (Session session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            var user = session.get(UserInfoEntity.class, key);
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public UserInfoEntity save(UserInfoEntity entity) {
        try (Session session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public UserInfoEntity update(UserInfoEntity entity) {
        try (Session session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            var user = session.merge(entity);
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public void delete(UserInfoEntity entity) {
        try (Session session = DatabaseUtil.INSTANCE.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }
}
