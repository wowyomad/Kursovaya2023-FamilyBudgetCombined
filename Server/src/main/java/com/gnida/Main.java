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


        try {
            ServerSocket socket = new ServerSocket(12345);
            Dispatcher dispatcher = context.getBean(Dispatcher.class);


            while (true) {
                Socket client = socket.accept();
                System.out.println("client connected");

                Thread clientThread = new Thread(() -> {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                        while (true) {
                            Object requestObject = ois.readObject();
                            Request request = (Request) requestObject;
                            System.out.println(request);
                            Response response = dispatcher.dispatch(request);
                            System.out.println(response);
                        }

                    } catch(SocketException e) {
                        System.out.println("Client disconnected");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                clientThread.start();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

