package com.gnida.service;

import com.gnida.DatabaseUtil;
import com.gnida.dao.UserDao;
import com.gnida.entity.UserEntity;
import org.hibernate.Session;

import java.util.List;

public class UserService implements UserDao {


    @Override
    public List<UserEntity> getAll() {
        try (Session session = DatabaseUtil.openSession()) {
            return session.createQuery("FROM UserEntity", UserEntity.class).getResultList();
        }
    }

    @Override
    public UserEntity get(Integer key) {
        try (Session session = DatabaseUtil.openSession()) {
            return session.get(UserEntity.class, key);
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        try (Session session = DatabaseUtil.openSession()) {
            session.beginTransaction();
            session.persist(userEntity);
            session.getTransaction().commit();
            return userEntity;
        }
    }
}
