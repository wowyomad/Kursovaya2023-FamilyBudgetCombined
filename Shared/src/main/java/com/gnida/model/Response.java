package com.gnida.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {
    Status status;
    Object object;
    String message;

    public enum Status {
        OK,
        NOT_FOUND,
        CONFLICT, BAD_REQUEST, DAUN_NA_RAZRABE, DAUN_NA_POLZOVATELE, NO_CONTENT, SESSION_NOT_FOUND, NOT_ACTIVE, ACCESS_DENIED
    }

    public static Response UserNotFound = Response.builder()
            .status(Status.NO_CONTENT)
            .message("Пользователь не найден")
            .build();

    public static Response UserSessionNotFound = Response.builder()
            .status(Status.SESSION_NOT_FOUND)
            .message("Получен запрос, но сессия не найдена")
            .build();

    public static Response UserAlreadyExist = Response.builder()
            .status(Status.CONFLICT)
            .message("User already exists")
            .build();


    public static Response UnknownMethod = Response.builder()
            .status(Status.BAD_REQUEST)
            .message("Такого запроса не существует")
            .build();

    public static Response WrongEntityParameters = Response.builder()
            .status(Status.BAD_REQUEST)
            .message("Некорректный объект в теле запроса")
            .build();

    public static Response IncorrectDataPassed = Response.builder()
            .status(Status.DAUN_NA_POLZOVATELE)
            .message("Некорректные данные в теле запроса")
            .build();

}
