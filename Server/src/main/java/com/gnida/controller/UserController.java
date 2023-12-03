package com.gnida.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.ClientSessionNotFound;
import com.gnida.Main;
import com.gnida.Server;
import com.gnida.converter.Converter;
import com.gnida.entity.User;
import com.gnida.entity.UserInfo;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.mapping.UpdateMapping;
import com.gnida.mappings.Mapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.AuthService;
import com.gnida.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class UserController implements IController {
    ObjectMapper mapper = Converter.getInstance();
    ModelMapper modelMapper = new ModelMapper();

    @NonNull
    UserService userService;

    @NonNull
    AuthService authService;

    @GetMapping(Mapping.User.all)
    public Response findAll(Request request) {
        List<User> users = userService.findAll();
        return Response.builder()
                .status(Response.Status.OK)
                .object(users)
                .build();
    }

    @PostMapping(Mapping.User.register)
    public Response register(Request request) {
        User user;
        try {
            user = (User) request.getObject();
        } catch (ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
        Response response = authService.register(user.getLogin(), user.getPassword());
        if (Response.Status.OK.equals(response.getStatus())) {
            user = (User) response.getObject();
            setUserSession(request.getSessionId(), user);
        }
        return response;
    }

    @PostMapping(Mapping.User.login)
    public Response login(Request request) {
        User user;
        try {
            user = (User) request.getObject();
        } catch (ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
        Response response = authService.login(user.getLogin(), user.getPassword());
        if (Response.Status.OK.equals(response.getStatus())) {
            user = (User) response.getObject();
            if (!user.isActive()) {
                return Response.builder()
                        .status(Response.Status.NOT_ACTIVE)
                        .message("Пользователь не активен")
                        .build();
            }
            setUserSession(request.getSessionId(), user);
        }
        return response;
    }


    @UpdateMapping(Mapping.User.info)
    public Response updateInfo(Request request) {
        UserInfo info;
        User user;
        try {
            info = (UserInfo) request.getObject();
        } catch (ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
        try {
            user = Server.getInstance().getUserInfo(request.getSessionId());
        } catch (ClientSessionNotFound e) {
            return Response.UserSessionNotFound;
        }
        user.setInfo(info);
        userService.save(user);
        return Response.builder()
                .status(Response.Status.OK)
                .object(Converter.toJson(user))
                .message("Информация о пользователе сохранена")
                .build();
    }

    private void setUserSession(UUID id, User user) {
        try {
            Main.getBean(Server.class).putUserInfo(id, user);
        } catch (ClientSessionNotFound e) {
            throw new RuntimeException(e);
        }
    }

}
