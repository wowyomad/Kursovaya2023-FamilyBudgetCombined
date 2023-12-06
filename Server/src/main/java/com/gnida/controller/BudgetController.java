package com.gnida.controller;

import com.gnida.ClientSessionNotFound;
import com.gnida.Server;
import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.mapping.DeleteMapping;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.mapping.UpdateMapping;
import com.gnida.mappings.Mapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.BudgetService;
import com.gnida.service.UserBudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.aot.PublicMethodReflectiveProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.xml.transform.OutputKeys;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BudgetController implements IController {

    @NonNull
    BudgetService budgetService;

    @NonNull
    UserBudgetService userBudgetService;

    @PostMapping(Mapping.Budget.budget)
    public Response add(Request request) {
        User user;
        try {
            user = Server.getInstance().getUserInfo(request.getSessionId());
            if (user == null) throw new ClientSessionNotFound();
        } catch (ClientSessionNotFound e) {
            return Response.UserSessionNotFound;
        }
        Budget budget = (Budget) request.getObject();
        Budget savedBudget = budgetService.save(budget);
        userBudgetService.save(user, savedBudget);
        return Response.builder()
                .status(Response.Status.OK)
                .message("Бюджет успешно добавлен")
                .build();
    }

    @DeleteMapping(Mapping.Budget.budget)
    public Response delete(Request request) {
        try {
            Budget budget = (Budget) request.getObject();
            budgetService.delete(budget);
            return Response.builder().status(Response.Status.OK).build();
        } catch(ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
    }

    @UpdateMapping(Mapping.Budget.budget)
    public Response update(Request request) {
        try {
            Budget budget = (Budget) request.getObject();
            Optional<Budget> result = budgetService.update(budget);
            if(result.isPresent()) {
                return Response.builder()
                        .status(Response.Status.OK)
                        .message("Информация о бюджете успешно обновлена")
                        .build();
            } else {
                return Response.builder()
                        .status(Response.Status.DAUN_NA_RAZRABE)
                        .message("Не получилось сохранить бюджет")
                        .build();
            }
        } catch(ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
    }

    @GetMapping(Mapping.Budget.current_user)
    public Response findByCurrentUser(Request request) {
        User currentUser = null;
        try {
            currentUser = Server.getInstance().getUserInfo(request.getSessionId());
            request.setObject(currentUser);
        } catch (ClientSessionNotFound e) {
            e.printStackTrace();
            return Response.IncorrectDataPassed;
        }
        return findByUserId(request);
    }

    @GetMapping(Mapping.Budget.user)
    public Response findByUserId(Request request) {
        User user = (User) request.getObject();
        int userId = user.getId();
        List<Budget> budgets = budgetService.findAllByUserId(userId);
        budgets.forEach(budget -> {
            User owner = userBudgetService.findOwnerByBudgetId(budget);
            budget.setOwner(owner);
        });
        return Response.builder()
                .status(Response.Status.OK)
                .message("Найдено " + budgets.size() + " бюджетов")
                .object(budgets)
                .build();

    }


}