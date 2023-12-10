package com.gnida;

import com.gnida.controller.Dispatcher;
import com.gnida.entity.User;
import com.gnida.logging.LOGGER;
import com.gnida.model.Request;
import com.gnida.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

@Component
@Scope(value = "prototype")
public class ClientThread implements Runnable {

    private Socket clientSocket;
    private User user;

    private UUID clientSessionId;
    private Dispatcher dispatcher;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;


    @Autowired
    public ClientThread(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        if (clientSessionId == null) {
            throw new RuntimeException();
        }
        try {
            while (clientSocket.isConnected()) {
                Request request = (Request) objectIn.readObject();
                request.setSessionId(clientSessionId);
                LOGGER.log(request.toString());
                Response response = dispatcher.dispatch(request);
                LOGGER.log(response.toString());
                objectOut.writeObject(response);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Клиент отключен");
    }


    public void obtainStreams(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            objectIn = new ObjectInputStream(clientSocket.getInputStream());
            objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Не удалось обменяться стримами с клиентом");
            e.printStackTrace();
        }
    }

    public void setClientSessionId(UUID clientSessionId) {
        this.clientSessionId = clientSessionId;
    }
}
