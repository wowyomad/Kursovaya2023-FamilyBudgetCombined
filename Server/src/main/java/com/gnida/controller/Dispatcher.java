package com.gnida.controller;

import com.gnida.model.Request;
import com.gnida.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class Dispatcher {

    private final UserController userController;
    private final BudgetController budgetController;
    private final UserBudgetController userBudgetController;
    private final CategoryController categoryController;
    private final SubcategoryController subcategoryController;
    private final TransactionController transactionController;


    public Response dispatch(Request request) {
        IController controller = switch(request.getPath()) {
            case BUDGET -> budgetController;
            case USER -> userController;
        };

        if (controller == null) {
            return Response.builder().status(Response.Status.BAD_REQUEST).message("No such path available").build();
        }

        String endPoint = request.getEndPoint();

        return switch (request.getType()) {
            case GET -> controller.handleGet(request);
            case POST -> controller.handlePost(request);
            case DELETE -> controller.handleDelete(request);
            case UPDATE -> controller.handleUpdate(request);
        };
    }

    public static void processAnnotations(Object object, String desiredEndpoint, Object... parameters) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            Mapping mapping = method.getAnnotation(Mapping.class);
            if (mapping != null && mapping.value().equals(desiredEndpoint)) {
                System.out.println("Processing method: " + method.getName() + " with endpoint: " + desiredEndpoint);
                method.invoke(object, parameters);
                break;
            }
        }
    }
}






