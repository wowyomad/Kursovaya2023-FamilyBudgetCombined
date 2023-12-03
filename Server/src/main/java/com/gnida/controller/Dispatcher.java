package com.gnida.controller;

import com.gnida.Server;
import com.gnida.mapping.DeleteMapping;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.mapping.UpdateMapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class Dispatcher {

    private final UserController userController;
    private final BudgetController budgetController;
    private final UserBudgetController userBudgetController;
    private final CategoryController categoryController;
    private final TransactionController transactionController;

    @NonNull
    private final Server server;


    public Response dispatch(Request request) {
        IController controller = switch (request.getRoute()) {
            case BUDGET -> budgetController;
            case USER -> userController;
            case TRANSACTION -> transactionController;
            case CATEGORY -> categoryController;
        };

        if (controller == null) {
            return Response.builder().status(Response.Status.BAD_REQUEST).message("No such path available").build();
        }
        Class<? extends Annotation> mapping = switch (request.getType()) {
            case GET -> GetMapping.class;
            case POST -> PostMapping.class;
            case DELETE -> DeleteMapping.class;
            case UPDATE -> UpdateMapping.class;
            default -> throw new RuntimeException("Это как?");
        };
        String endPoint = request.getEndPoint();
        return processMethod(controller, endPoint, mapping, request);
    }

    public static Response processMethod(IController controller,
                                         String desiredEndpoint,
                                         Class<? extends Annotation> annotationClass,
                                         Request request) {
        Method[] methods = controller.getClass().getDeclaredMethods();
        try {
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                Annotation annotation = method.getAnnotation(annotationClass);
                if (annotation != null) {
                    String value = (String) annotationClass.getMethod("value").invoke(annotation);
                    if (value.equals(desiredEndpoint)) {
                        if (method.getReturnType() != Response.class) {
                            throw new RuntimeException("Return type of method is not Response. Method: " + method.getName());
                        }
                        System.out.println("Processing method: " + method.getName() + " with endpoint: " + desiredEndpoint);
                        return (Response) method.invoke(controller, request);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return Response.UnknownMethod;
    }
}






