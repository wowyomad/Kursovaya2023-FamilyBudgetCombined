package com.gnida;

import com.gnida.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        var service = context.getBean(UserService.class);
        service.findByLoginAndPassword("123", "123");

    }
}

