package com.gnida;

import com.gnida.model.Request;
import com.gnida.model.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Component
public class Client {

    private Socket socket;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    @Value("${app.port}")
    private Integer port;

    @Value("${app.address}")
    private String host;

    @PostConstruct
    private void init() {
        try {
            socket = new Socket(host, port);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response sendRequest(Request request) {
        try {
            objectOut.writeObject(request);
            return (Response) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreDestroy
    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (objectIn != null) {
                objectIn.close();
            }
            if (objectOut != null) {
                objectOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
