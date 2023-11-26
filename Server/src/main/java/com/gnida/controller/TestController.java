package com.gnida.controller;

import com.gnida.model.Response;

public class TestController {

    @Mapping("/all")
    public void getAllUsers() {

    }

    @Mapping("/id")
    public void getUserById(Integer id) {

    }

    @Mapping("/login")
    public void login(String login, String password) {

    }

    @Mapping("/register")
    public void register(String login, String password) {

    }
}
