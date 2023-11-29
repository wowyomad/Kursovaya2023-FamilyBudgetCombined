package com.gnida.service;

import com.gnida.entity.User;
import com.gnida.enums.UserRole;

import java.util.List;


public interface UserService {
    List<User> findAll();

    User findById(Integer key);

    User save(User user);

    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByRole(UserRole role);

}
