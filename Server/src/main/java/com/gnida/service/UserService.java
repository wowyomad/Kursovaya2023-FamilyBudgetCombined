package com.gnida.service;

import com.gnida.entity.User;
import com.gnida.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Integer key) {
        return repository.findById(key).orElseGet(() -> null);
    }

    public void save(User user) {
        repository.save(user);
    }

    public User findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }
}

