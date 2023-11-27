package com.gnida.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.converter.Converter;
import com.gnida.domain.UserDto;
import com.gnida.entity.User;
import com.gnida.enums.UserRole;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.AuthService;
import com.gnida.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class UserController implements IController {
    ObjectMapper objectMapper = new ObjectMapper();
    ModelMapper modelMapper = new ModelMapper();
    @NonNull
    UserService userService;

    @NonNull
    AuthService authService;

    @GetMapping("/all")
    public Response getUsers(Request request) {
        List<UserDto> users = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        String json = Converter.toJson(users);
        return Response.builder()
                .status(Response.Status.OK)
                .json(json)
                .build();
    }

    @PostMapping("/register")
    public Response register(Request request) {

        try {
            JsonNode node = objectMapper.readTree(request.getJson());
            String login = node.get("login").asText();
            String password = node.get("password").asText();
            return authService.register(login, password);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.builder()
                    .status(Response.Status.DAUN_NA_POLZOVATELE)
                    .message("Wrong data passed. Expected {\"login\":{login},\"password\":{password}")
                    .build();
        }
    }

    @PostMapping("/login")
    public Response login(Request request) {
        try {
            JsonNode node = objectMapper.readTree(request.getJson());
            String login = node.get("login").asText();
            String password = node.get("password").asText();
            return authService.login(login, password);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.builder()
                    .status(Response.Status.DAUN_NA_POLZOVATELE)
                    .message("Wrong data passed. Expected {\"login\":{login},\"password\":{password}")
                    .build();
        }
    }

    @GetMapping("/all")
    public Response handleGet(Request request) {

        List<UserDto> users = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        try {
            String usersJson = objectMapper.writeValueAsString(users);
            return Response.builder().status(Response.Status.OK).json(usersJson).build();
        } catch (JsonProcessingException e) {
            return Response.builder()
                    .status(Response.Status.DAUN_NA_RAZRABE)
                    .message("Json parsing issue")
                    .build();
        }


    }

    public Response handlePost(Request request) {
        return null;
    }

    public Response handleDelete(Request request) {
        return null;
    }

    public Response handleUpdate(Request request) {
        return null;
    }


}
