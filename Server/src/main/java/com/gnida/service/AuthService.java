package com.gnida.service;


import com.gnida.model.Response;

public interface AuthService {
    Response login(String login, String password);
    Response register(String login, String password);

}
