package com.gnida;

import com.gnida.entity.UserEntity;
import com.gnida.entity.UserInfoEntity;
import com.gnida.service.UserInfoService;
import com.gnida.service.UserService;
import org.codehaus.plexus.util.StringUtils;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        UserEntity ue = new UserEntity();
        ue.setPassword("123");
        ue.setLogin("vadim");
        service.save(ue);
        var list = service.getAll();
        System.out.println(list);



    }
}