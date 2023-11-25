package com.gnida.service;

import com.gnida.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    List<User> findAll();

    User findById(Integer key);

    void save(User user);

    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);

    boolean existsByLogin(String login);
}
