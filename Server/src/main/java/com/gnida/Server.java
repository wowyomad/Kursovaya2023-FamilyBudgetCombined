package com.gnida;

import com.mysql.cj.xdevapi.Client;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@Component
@Scope(value = "singleton")
@RequiredArgsConstructor
public class Server {
    @Value("${app.address}")
    private String address;
    @Value("${app.port}")
    private Integer port;
    @Value("${app.backlog}")
    private Integer backlog;
    private final List<Socket> clientSockets;

    private ServerSocket serverSocket;

    private boolean isStarted;

    private ApplicationContext context;
    public void setContext(ApplicationContext context) {
        this.context = context;
    }



    public void start() {
        if (isStarted) {
            return;
        }
        isStarted = true;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(address, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (isStarted) {
            clientSockets.removeIf(Socket::isClosed);
            clientSockets.forEach(client -> {
                String info = "Клиент " + client.getInetAddress() + ":" + client.getPort() + " подключен.";
                System.out.println(info);
            });

            try {
                Socket client = serverSocket.accept();
                clientSockets.add(client);
                Thread clientThread = new Thread(new ClientThread(client, context));
                clientThread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void stop() {
        isStarted = false;
        for (Socket client : clientSockets) {
            if(!client.isClosed()) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }



}
