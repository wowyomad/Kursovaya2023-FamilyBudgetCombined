package com.gnida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gnida.converter.Converter;
import com.gnida.domain.UserDto;
import com.gnida.model.Request;
import com.gnida.model.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SpringApplication.run(ClientMain.class, args);


        Socket s = new Socket("localhost", 12345);
        System.out.println("Connected");

        Map<String, String> map = new HashMap<>();
        map.put("login", "root");
        map.put("password", "root");

        String json = Converter.toJson(map);

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        System.out.println("got streams");
        TypeReference<List<UserDto>> typeReference = new TypeReference<List<UserDto>>() {
        };

        Request request = Request.builder()
                .type(Request.RequestType.POST)
                .endPoint("/register")
                .path(Request.Path.USER)
                .json(json)
                .build();
        oos.writeObject(request);


        System.out.println(request);
        Object responseObject = ois.readObject();
        Response response = (Response) responseObject;
        System.out.println(response);


    }
}