package com.gnida.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.domain.UserDto;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class UserController implements IController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response handleGet(Request request) {
        ObjectMapper objectMapper = new ObjectMapper();
        ModelMapper modelMapper = new ModelMapper();

        String json = request.getJson();
        if (json == null || json.isEmpty()) {
            List<UserDto> users = userService.findAll().stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .toList();
            try {
                String usersJson = objectMapper.writeValueAsString(users);
                return Response.builder().status(Response.Status.OK).json(usersJson).build();
            } catch (JsonProcessingException e) {
                return Response.builder()
                        .status(Response.Status.DAUN_NA_RAZRABE)
                        .message("Не получилось замапить")
                        .build();
            }
        }
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            User user = getUserFromJson(rootNode);
            if (user == null) {
                return Response.UserNotFound;
            }
            UserDto userDto = modelMapper.map(user, UserDto.class);
            String userJson = objectMapper.writeValueAsString(userDto);
            return Response.builder()
                    .status(Response.Status.OK)
                    .json(userJson)
                    .build();

        } catch (
                JsonProcessingException e) {
            return Response.builder()
                    .status(Response.Status.DAUN_NA_POLZOVATELE)
                    .message("Invalid JSON format")
                    .build();
        }

    }

    @Override
    public Response handlePost(Request request) {
        return null;
    }

    @Override
    public Response handleDelete(Request request) {
        return null;
    }

    @Override
    public Response handleUpdate(Request request) {
        return null;
    }

    private Path getPathFromJson(JsonNode jsonNode) {
        if (jsonNode.has("id")) {
            return Path.ID;
        } else if (jsonNode.has("login") && jsonNode.has("password")) {
            return Path.LOGIN_PASSWORD;
        } else if (jsonNode.has("login")) {
            return Path.LOGIN;
        } else {
            return Path.UNKNOWN;
        }
    }

    private User getUserFromJson(JsonNode node) {
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = getPathFromJson(node);
        User user = null;
        return switch (path) {
            case ID -> userService.findById(node.get("id").asInt());
            case LOGIN -> userService.findByLogin(node.get("login").asText());
            case LOGIN_PASSWORD -> userService.findByLoginAndPassword(
                    node.get("login").asText(),
                    node.get("password").asText());
            case UNKNOWN -> null;
        };
    }
    private enum Path {
        ID,
        LOGIN,
        LOGIN_PASSWORD,
        UNKNOWN
    }

// Implement methods
}
