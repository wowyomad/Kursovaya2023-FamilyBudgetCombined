package com.gnida;

import com.gnida.entity.Budget;
import com.gnida.model.Request;
import com.gnida.model.Response;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.NotYetConnectedException;

@Component
@Scope("singleton")
public class Client{
    private Socket socket;
    private boolean isConnected;

    private Budget openedBudget;
    void openBudget(Budget budget) {
        openedBudget = budget;
    }
    void closeBudget() {
        this.openedBudget = null;
    }

    public boolean isConnected() {return this.isConnected;}
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    @Value("${app.port}")
    private Integer port;

    @Value("${app.address}")
    private String host;

    @PostConstruct
    private void init() {
        if(isConnected) return;

        try {
            socket = new Socket(host, port);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        isConnected = true;
    }

    public Response sendRequest(Request request) throws NotYetConnectedException{
        if(!isConnected) throw new NotYetConnectedException();
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
        isConnected = false;
    }

}
