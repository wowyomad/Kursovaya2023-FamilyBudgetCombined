package com.gnida.service;

import com.gnida.DatabaseUtil;
import com.gnida.dao.UserInfoDao;
import com.gnida.entity.UserEntity;
import com.gnida.entity.UserInfoEntity;

import java.util.List;

public class UserInfoService implements UserInfoDao {

    @Override
    public List<UserInfoEntity> getAll() {
        try(var session = DatabaseUtil.INSTANCE.openSession()) {
            return session.createQuery("FROM UserInfoEntity", UserInfoEntity.class).getResultList();
        }
    }

    @Override
    public UserInfoEntity get(UserEntity key) {
        try (var session = DatabaseUtil.INSTANCE.openSession()) {
            return session.createQuery("FROM UserInfoEntity WHERE user = :user", UserInfoEntity.class)
                    .setParameter("user", key)
                    .uniqueResult();
        }
    }

    @Override
    public UserInfoEntity save(UserInfoEntity userInfoEntity) {
        try (var session = DatabaseUtil.INSTANCE.openSession()) {
            session.persist(userInfoEntity);
            return userInfoEntity;
        }
    }
}
