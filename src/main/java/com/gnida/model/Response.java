package com.gnida.model;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Response implements Serializable {
    Status status;
    String json_body;
    String message;

    public enum Status {
        OK,
        NOT_FOUND,
        CONFLICT, ACCESS_DENIED
    }
}
