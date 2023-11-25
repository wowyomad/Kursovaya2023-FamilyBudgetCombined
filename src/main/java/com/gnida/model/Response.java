package com.gnida.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Response implements Serializable {
    Status status;
    String json;
    String message;

    public enum Status {
        OK,
        NOT_FOUND,
        CONFLICT, BAD_REQUEST, DAUN_NA_RAZRABE, DAUN_NA_POLZOVATELE, NO_CONTENT, ACCESS_DENIED
    }

    public static Response UserNotFound = Response.builder()
            .status(Status.NO_CONTENT)
            .message("User not found")
            .build();

    public static Response UserAlreadyExist = Response.builder()
            .status(Status.CONFLICT)
            .message("User already exists")
            .build();

    public static Response UnknownJson = Response.builder()
            .status(Status.NOT_FOUND)
            .message("Json doesn't contain supported paramters")
            .build();


}
