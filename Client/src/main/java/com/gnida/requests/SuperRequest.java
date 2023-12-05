package com.gnida.requests;

import com.gnida.mappings.Mapping;
import com.gnida.model.Request;

public final class SuperRequest {
    public static final Request.RequestBuilder GET_BUDGETS_ALL = Request.builder()
            .type(Request.RequestType.GET)
            .route(Request.Route.BUDGET)
            .endPoint("/all");

    public static final Request.RequestBuilder POST_USER_REGISTER = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.USER)
            .endPoint("/register");

    public static final Request.RequestBuilder POST_USER_LOGIN = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.USER)
            .endPoint("/login");

    public static Request.RequestBuilder GET_TRANSACTIONS_BY_BUDGET  = Request.builder()
            .type(Request.RequestType.GET)
            .route(Request.Route.TRANSACTION)
            .endPoint(Mapping.Transaction.budget);
}
