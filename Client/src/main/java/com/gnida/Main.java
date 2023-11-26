package com.gnida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.converter.Converter;
import com.gnida.domain.UserDto;
import com.gnida.model.Request;
import com.gnida.model.Response;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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