package com.gnida.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request implements Serializable {
    RequestType type;
    Path path;
    String endPoint;
    String json;

    public enum Path {
        BUDGET,
        USER,
        TRANSACTION,
        CATEGORY,
        SUBCATEGORY
    }


    public enum RequestType {
        GET,
        POST,
        DELETE,
        UPDATE
    }
}
