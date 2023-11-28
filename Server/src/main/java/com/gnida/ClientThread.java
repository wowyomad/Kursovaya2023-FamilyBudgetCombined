package com.gnida;

import com.gnida.controller.Dispatcher;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

@Component
@Scope(value = "prototype")
public class ClientThread implements Runnable{

    private final Socket client;

    private  User user;

    private Dispatcher dispatcher;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    @Autowired
    private ApplicationContext context;

    public ClientThread(@Nullable Socket client, @Nullable ApplicationContext context) {
        this.context = context;
        this.client = client;
        try {
            objectIn = new ObjectInputStream(client.getInputStream());
            objectOut = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.out.println("Не удалось обменяться стримами с клиентом");
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        dispatcher = context.getBean(Dispatcher.class);
        try {
            while(client.isConnected()) {
                Request request = (Request) objectIn.readObject();
                System.out.println(request);
                Response response = dispatcher.dispatch(request);
                System.out.println(response);
                objectOut.writeObject(response);
            }
        } catch (SocketException e) {

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //ничё
        }
        System.out.println("Client disconnected");
    }
}
