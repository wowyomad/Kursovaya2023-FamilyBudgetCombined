package com.gnida;

import com.gnida.entity.User;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

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

    public static Server getInstance() {
        return Main.getBean(Server.class);
    }

    private final Queue<Pair<UUID, Socket>> clientSessions = new ConcurrentLinkedDeque<>();
    private final Queue<Socket> clientSockets = new ConcurrentLinkedDeque<>();

    private final Map<UUID, User> clientUids = new HashMap<>();

    public void putUserInfo(UUID id, User user) throws ClientSessionNotFound {
        if (!clientUids.containsKey(id)) {
            throw new ClientSessionNotFound();
        }
        clientUids.put(id, user);
    }

    public User getUserInfo(UUID id) throws ClientSessionNotFound {
        if (!clientUids.containsKey(id)) {
            throw new ClientSessionNotFound();
        }
        return clientUids.get(id);
    }

    private boolean isStarted;

    private ApplicationContext context;

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private Thread createThreadWithSession(Socket socket) {
        UUID id = UUID.randomUUID();
        clientSessions.add(new Pair<>(id, socket));
        clientUids.put(id, null);
        ClientThread clientThread = context.getBean(ClientThread.class);
        clientThread.obtainStreams(socket);
        clientThread.setClientSessionId(id);
        return new Thread(clientThread);
    }


    public void start() {
        if (isStarted) {
            return;
        }
        isStarted = true;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(address, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (isStarted) {
            clientSockets.removeIf(Socket::isClosed);

            Iterator<Pair<UUID, Socket>> iterator = clientSessions.iterator();
            while (iterator.hasNext()) {
                Pair<UUID, Socket> entry = iterator.next();
                if (entry.getValue().isClosed()) {
                    clientUids.remove(entry.getKey());
                    iterator.remove();
                }
            }
            clientSessions.forEach(client -> {
                Socket socket = client.getValue();
                if (socket.isClosed()) {
                    clientUids.remove(client.getKey());
                    clientSessions.remove(client);
                } else {
                    String info = "Клиент " + socket.getInetAddress() + ":" + socket.getPort() + " подключен.";
                    System.out.println(info);
                }
            });

            try {
                Socket clientSocket = serverSocket.accept();
                Thread thread = createThreadWithSession(clientSocket);
                thread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void stop() {
        isStarted = false;
        for (var session : clientSessions) {
            Socket socket = session.getValue();
            if (socket != null && socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
