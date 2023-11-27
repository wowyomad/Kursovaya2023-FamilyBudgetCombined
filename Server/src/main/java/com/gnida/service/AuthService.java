package com.gnida.service;


import com.gnida.model.Response;
import org.springframework.stereotype.Service;

public interface AuthService {
    Response login(String login, String password);
    Response register(String login, String password);

}
