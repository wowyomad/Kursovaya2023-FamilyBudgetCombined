package com.gnida.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Request implements Serializable {
    RequestType type;
    Path path;
    String json;

    public enum Path {
        BUDGET,
        USER,
        REGISTER,
        LOGIN
    }


    public enum RequestType {
        GET,
        POST,
        DELETE,
        UPDATE
    }
}
