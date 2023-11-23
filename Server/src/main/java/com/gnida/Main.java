package com.gnida;

import com.gnida.entity.UserEntity;
import com.gnida.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        UserEntity ue = new UserEntity();
        ue.setPassword("123");
        ue.setLogin("12321");
        service.save(ue);
        var list = service.getAll();
        System.out.println(list);



    }
}