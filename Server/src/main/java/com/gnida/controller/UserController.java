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
import com.gnida.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class UserController implements IController {
    ObjectMapper objectMapper = new ObjectMapper();
    ModelMapper modelMapper = new ModelMapper();
    @NonNull UserService userService;

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

            if(login(request) != Response.UserNotFound) {
                return Response.UserAlreadyExist;
            }

            String login = node.get("login").asText();
            String password = node.get("password").asText();
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);

            if(!userService.existsByRole(UserRole.ADMIN)) {
                user.setRole(UserRole.ADMIN);
            } else {
                user.setRole(UserRole.USER);
            }

            userService.save(user);
            return Response.builder()
                    .status(Response.Status.OK)
                    .json(Converter.toJson(user))
                    .message("New user registered")
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public Response login(Request request) {
        try {
            JsonNode node = objectMapper.readTree(request.getJson());
            if(!node.has("login") || !node.has("password")) {
                return Response.WrongJsonParameters;
            }

            String login = node.get("login").asText();
            String password = node.get("password").asText();

            User user = userService.findByLoginAndPassword(login, password);
            if (user == null) {
                return Response.UserNotFound;
            }

            return Response.builder()
                    .status(Response.Status.OK)
                    .json(Converter.toJson(user))
                    .message("Logged in")
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    @GetMapping("/all")
    public Response handleGet(Request request) {

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

    public Response handlePost(Request request) {
        return null;
    }

    public Response handleDelete(Request request) {
        return null;
    }

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
