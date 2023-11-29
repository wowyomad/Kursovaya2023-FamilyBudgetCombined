package com.gnida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Main {
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static void main(String[] args) {
        context = SpringApplication.run(Main.class, args);
        Server server = context.getBean(Server.class);
        server.setContext(context);
        server.start();
    }
}

