package com.gnida.controller;

import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.model.Request;
import com.gnida.model.Response;

public class TestController implements IController {

    @GetMapping("/all")
    public Response getAllUsers(Request request) {
        try {
            System.out.println(this.getClass().getMethod("getAllUsers", Request.class).getName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return Response.builder().status(Response.Status.OK).build();
    }

    @GetMapping("/id")
    public Response getUserById(Request request) {
        try {
            System.out.println(this.getClass().getMethod("getUserById", Request.class).getName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return Response.builder().status(Response.Status.OK).build();
    }

    @PostMapping("/login")
    public Response login(Request request) {
        try {
            System.out.println(this.getClass().getMethod("login", Request.class).getName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return Response.builder().status(Response.Status.OK).build();
    }

    @PostMapping("/register")
    public Response register(Request request) {
        try {
            System.out.println(this.getClass().getMethod("register", Request.class).getName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return Response.builder().status(Response.Status.OK).build();
    }

}
