package com.gnida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.domain.UserDto;
import com.gnida.model.Request;
import com.gnida.model.Response;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 12345);
        System.out.println("Connected");

        TypeReference<List<UserDto>> typeReference = new TypeReference<List<UserDto>>(){};

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Got streams");

        ObjectMapper mapper = new ObjectMapper();
        Request request = Request.builder()
                .type(Request.RequestType.GET)
                .path(Request.Path.USER)
                .build();
        String jsonRequest = mapper.writeValueAsString(request);
        out.write(jsonRequest + '\n');
        out.flush();
        System.out.println("sent");
        System.out.println("waiting");
        String jsonResopnse = in.readLine();
        Response response = mapper.readValue(jsonResopnse, Response.class);

        List<UserDto> list = mapper.readValue(response.getJson(), typeReference);
        System.out.println(list);
    }
}