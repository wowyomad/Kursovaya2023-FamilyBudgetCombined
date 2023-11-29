package com.gnida.service.impl;

import com.gnida.entity.User;
import com.gnida.enums.UserRole;
import com.gnida.repository.UserRepository;
import com.gnida.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @NonNull
    UserRepository repository;


    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Integer key) {
        return repository.findById(key).orElseGet(() -> null);
    }

    @Override
    public User save(User user) { return repository.save(user) ;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password).orElseGet(() -> null);
    }

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login).orElseGet(() -> null);
    }

    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    @Override
    public boolean existsByRole(UserRole role) {return repository.existsByRole(role);}


}


