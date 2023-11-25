package com.gnida.controller;

import com.gnida.model.Request;
import com.gnida.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Dispatcher {

    private final UserController userController;
    private final BudgetController budgetController;


    public Response dispatch(Request request) {
        IController controller = switch(request.getPath()) {
            case BUDGET -> budgetController;
            case USER -> userController;
            case REGISTER -> null;
            case LOGIN -> null;
        };

        if (controller == null) {
            return Response.builder().status(Response.Status.BAD_REQUEST).message("No such path available").build();
        }
        return switch (request.getType()) {
            case GET -> controller.handleGet(request);
            case POST -> controller.handlePost(request);
            case DELETE -> controller.handleDelete(request);
            case UPDATE -> controller.handleUpdate(request);
        };
    }

}






