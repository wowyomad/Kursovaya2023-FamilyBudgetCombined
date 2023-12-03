package com.gnida.controller;

import com.gnida.ClientSessionNotFound;
import com.gnida.Server;
import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.mappings.Mapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.BudgetService;
import com.gnida.service.UserBudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return Response.builder()
                .status(Response.Status.OK)
                .message("Найдено " + budgets.size() + " бюджетов")
                .object(budgets)
                .build();

    }


}