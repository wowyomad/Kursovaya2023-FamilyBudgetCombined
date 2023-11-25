package com.gnida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.controller.Dispatcher;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;


@SpringBootApplication
public class Main {
    public static ApplicationContext context;


    public static void main(String[] args) {
        context = SpringApplication.run(Main.class, args);

        Dispatcher dispatcher = context.getBean(Dispatcher.class);
        Request request = Request.builder()
                .type(Request.RequestType.GET)
                .path(Request.Path.USER)
                .build();

        Response resopnse = dispatcher.dispatch(request);
        List<User> users = new ObjectMapper().convertValue(resopnse.getJson(), new TypeReference<List<User>>(){});
        System.out.println(resopnse.getJson());


    }
}

