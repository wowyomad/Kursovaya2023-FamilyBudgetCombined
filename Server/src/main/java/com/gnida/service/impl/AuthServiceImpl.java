package com.gnida.service.impl;

import com.gnida.converter.Converter;
import com.gnida.entity.User;
import com.gnida.enums.UserRole;
import com.gnida.model.Response;
import com.gnida.service.AuthService;
import com.gnida.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @NonNull
    UserService userService;


    @Override
    public Response login(String login, String password) {
        User user = userService.findByLoginAndPassword(login, password);
        if (user == null) {
            return Response.builder()
                    .status(Response.Status.NOT_FOUND)
                    .message("User not found")
                    .build();
        }

        return Response.builder()
                .status(Response.Status.OK)
                .message("Logged in")
                .json(Converter.toJson(user))
                .build();

    }

    @Override
    public Response register(String login, String password) {
        if (userService.findByLogin(login) != null) {
            return Response.builder()
                    .status(Response.Status.CONFLICT)
                    .message("Login is taken")
                    .build();
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        if (!userService.existsByRole(UserRole.ADMIN)) {
            user.setRole(UserRole.ADMIN);
        } else {
            user.setRole(UserRole.USER);
        }
        userService.save(user);
        return Response.builder()
                .status(Response.Status.OK)
                .message("New user registered")
                .json(Converter.toJson(user))
                .build();
    }

}

