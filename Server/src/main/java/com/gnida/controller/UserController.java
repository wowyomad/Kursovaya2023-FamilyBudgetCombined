package com.gnida.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.ClientSessionNotFound;
import com.gnida.Main;
import com.gnida.Server;
import com.gnida.converter.Converter;
import com.gnida.domain.UserDto;
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
    public Response getUsers(Request request) {
        List<UserDto> users = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        String json = Converter.toJson(users);
        return Response.builder()
                .status(Response.Status.OK)
                .object(json)
                .build();
    }

    @PostMapping(Mapping.User.register)
    public Response register(Request request) {

        try {
            JsonNode node = mapper.readTree(request.getObject());
            String login = node.get("login").asText();
            String password = node.get("password").asText();
            Response response = authService.register(login, password);
            if(Response.Status.OK.equals(response.getStatus())) {
                User user = Converter.fromJson(response.getObject(), User.class);
                setSessionUser(request.getSessionId(), user);
            }
            return response;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.builder()
                    .status(Response.Status.DAUN_NA_POLZOVATELE)
                    .message("Wrong data passed. Expected {\"login\":{login},\"password\":{password}")
                    .build();
        }
    }

    @PostMapping(Mapping.User.login)
    public Response login(Request request) {
        try {
            JsonNode node = mapper.readTree(request.getObject());
            String login = node.get("login").asText();
            String password = node.get("password").asText();
            Response response = authService.login(login, password);
            if(Response.Status.OK.equals(response.getStatus())) {
                User user = Converter.fromJson(response.getObject(), User.class);
                if (!user.isActive()) {
                    return Response.builder()
                            .status(Response.Status.NOT_ACTIVE)
                            .message("Пользователь не активен")
                            .build();
                }
                setSessionUser(request.getSessionId(), user);
            }
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return Response.builder()
                    .status(Response.Status.DAUN_NA_POLZOVATELE)
                    .message("Wrong data passed. Expected {\"login\":{login},\"password\":{password}")
                    .build();
        }
    }


    @UpdateMapping(Mapping.User.info)
    public Response setInfo(Request request) {
        try {
            JsonNode node = mapper.readTree(request.getObject());
            String firstName = node.get("firstName").asText();
            String secondName = node.get("secondName").asText();
            UserInfo info = new UserInfo();
            info.setFirstName(firstName);
            info.setSecondName(secondName);
            User user = Server.getInstance().getUserInfo(request.getSessionId());
            if(user == null) {
                throw new ClientSessionNotFound();
            }
            user.setInfo(info);
            user = userService.save(user);
            return Response.builder()
                    .status(Response.Status.OK)
                    .object(Converter.toJson(user))
                    .message("User saved")
                    .build();

        } catch (ClientSessionNotFound e) {
            e.printStackTrace();
            return Response.builder()
                    .status(Response.Status.DAUN_NA_RAZRABE)
                    .message("No session for user")
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.builder()
                    .status(Response.Status.DAUN_NA_RAZRABE)
                    .message("Wrong data passed. Expected {'firstName'{firstName],'secondName':{secondName}}")
                    .build();
        }
    }

    private void setSessionUser(UUID id, User user) {
        try {
            Main.getBean(Server.class).putUserInfo(id, user);
        } catch (ClientSessionNotFound e) {
            throw new RuntimeException(e);
        }
    }

}
