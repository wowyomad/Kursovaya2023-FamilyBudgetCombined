package com.gnida;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setLogin("vadzim");
        user.setPassword("123");
        ObjectMapper mapper = new ObjectMapper();
        try (Socket s = new Socket("localhost", 1234);) {
            System.out.println("Connected to server");
            ObjectOutputStream ous = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            System.out.println("Got streams");
            String json = mapper.writeValueAsString(user);
            Request request = Request.builder().type(Request.RequestType.GET).path(Request.Path.USER).json(json).build();
            ous.writeObject(request);
            Response response = (Response) ois.readObject();
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}