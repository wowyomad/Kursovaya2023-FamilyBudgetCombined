package com.gnida;

import com.gnida.mapping.GetMapping;

public class Class {

    @GetMapping("/no-params")
    public void method1() {
        System.out.println("no params method");
    }

    @GetMapping("/string-param")
    public void method2(String string) {
        System.out.println("string param method: " + string);
    }

    @GetMapping("/string-param-return-string")
    public String method3(String string) {
        System.out.println("string param and return string method: " + string);
        return string;
    }
}
