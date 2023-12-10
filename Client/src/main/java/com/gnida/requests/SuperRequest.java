package com.gnida.requests;

import com.gnida.mappings.Mapping;
import com.gnida.model.Request;

public final class SuperRequest {
    public static final Request.RequestBuilder GET_BUDGETS_ALL = Request.builder()
            .type(Request.RequestType.GET)
            .route(Request.Route.BUDGET)
            .endPoint(Mapping.Budget.all);

    public static final Request.RequestBuilder POST_USER_REGISTER = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.USER)
            .endPoint(Mapping.User.register);

    public static final Request.RequestBuilder POST_USER_LOGIN = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.USER)
            .endPoint(Mapping.User.login);
    public static final Request.RequestBuilder DELETE_TRANSACTION_TRANSACTION = Request.builder()
            .type(Request.RequestType.DELETE)
            .route(Request.Route.TRANSACTION)
            .endPoint(Mapping.Transaction.transaction);
    public static final Request.RequestBuilder GET_BUDGETS_BY_CURRENT_USER = Request.builder()
            .type(Request.RequestType.GET)
            .route(Request.Route.BUDGET)
            .endPoint(Mapping.Budget.current_user);
    public static final Request.RequestBuilder DELETE_BUDGET_BUDGET = Request.builder()
            .type(Request.RequestType.DELETE)
            .route(Request.Route.BUDGET)
            .endPoint(Mapping.Budget.budget);
    public static final Request.RequestBuilder POST_BUDGET_BUDGET = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.BUDGET)
            .endPoint(Mapping.Budget.budget);
    public static final Request.RequestBuilder UPDATE_BUDGET_BUDGET = Request.builder()
            .type(Request.RequestType.UPDATE)
            .route(Request.Route.BUDGET)
            .endPoint(Mapping.Budget.budget);
    public static final Request.RequestBuilder GET_BUDGET_CATEGORIES_BY_BUDGET =Request.builder()
            .type(Request.RequestType.GET)
            .route(Request.Route.CATEGORY)
            .endPoint(Mapping.Category.budget);
    public static final Request.RequestBuilder POST_CATEGORY_CATEGORY = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.CATEGORY)
            .endPoint(Mapping.Category.category);

    public static Request.RequestBuilder GET_TRANSACTIONS_BY_BUDGET  = Request.builder()
            .type(Request.RequestType.GET)
            .route(Request.Route.TRANSACTION)
            .endPoint(Mapping.Transaction.budget);

    public static Request.RequestBuilder POST_TRANSACTION_BY_TRANSACTION = Request.builder()
            .type(Request.RequestType.POST)
            .route(Request.Route.TRANSACTION)
            .endPoint(Mapping.Transaction.transaction);
}
