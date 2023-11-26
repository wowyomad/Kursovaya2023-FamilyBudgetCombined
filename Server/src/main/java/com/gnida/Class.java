package com.gnida;

import com.gnida.controller.Mapping;

public class Class {

    @Mapping("/no-params")
    public void method1() {
        System.out.println("no params method");
    }

    @Mapping("/string-param")
    public void method2(String string) {
        System.out.println("string param method: " + string);
    }

    @Mapping("/string-param-return-string")
    public String method3(String string) {
        System.out.println("string param and return string method: " + string);
        return string;
    }
}
