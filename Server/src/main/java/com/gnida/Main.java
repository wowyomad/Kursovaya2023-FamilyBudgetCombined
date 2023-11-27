package com.gnida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.controller.Dispatcher;
import com.gnida.converter.Converter;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class Main {
    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Main.class, args);
        Server server = context.getBean(Server.class);
        server.setContext(context);
        server.start();
    }
}

