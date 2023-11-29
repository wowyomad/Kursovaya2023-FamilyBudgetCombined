package com.gnida.controller;

import com.gnida.ClientSessionNotFound;
import com.gnida.Server;
import com.gnida.converter.Converter;
import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.mapping.PostMapping;
import com.gnida.mappings.Mapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.BudgetService;
import com.gnida.service.UserBudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetController implements IController {

    @NonNull
    BudgetService budgetService;

    @NonNull
    UserBudgetService userBudgetService;

    @PostMapping(Mapping.Budget.all)
    public Response add(Request request) {
        Budget budget = null;
        User user = null;
        try {
            user = Server.getInstance().getUserInfo(request.getSessionId());
            if (user == null) throw new ClientSessionNotFound();
        } catch (ClientSessionNotFound e) {
            return Response.UserSessionNotFound;
        }
        String json = request.getJson();
        try {
            budget = Converter.fromJson(json, Budget.class);
        } catch (Exception e) {
            System.out.println("Не удалось прочитать json: " + json + ". Ожидался " + Budget.class.getName());
            return Response.WrongEntityParameters;
        }
        Budget savedBudget = budgetService.save(budget);
        if (savedBudget != null && userBudgetService.save(user, savedBudget) != null) {
            return Response.builder()
                    .status(Response.Status.OK)
                    .message("Бюджет успешно добавлен")
                    .json(Converter.toJson(savedBudget))
                    .build();
        } else {
            return Response.builder()
                    .status(Response.Status.DAUN_NA_RAZRABE)
                    .message("Не получилось добавить бюджет. Виноват сервер :(")
                    .build();
        }

    }

}