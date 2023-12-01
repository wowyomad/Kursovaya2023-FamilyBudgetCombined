package com.gnida.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnida.ClientSessionNotFound;
import com.gnida.Server;
import com.gnida.converter.Converter;
import com.gnida.domain.BudgetDto;
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

    @GetMapping("")
    public Response findByCurrentUser(Request request) {
        User currentUser = null;
        try {
            currentUser = Server.getInstance().getUserInfo(request.getSessionId());
        } catch (ClientSessionNotFound e) {
            e.printStackTrace();
            return Response.IncorrectDataPassed;
        }
        List<Budget> budgets = budgetService.findbyUserId(currentUser.getId());
        ObjectMapper mapper = new ObjectMapper();
        return Response.builder()
                .status(Response.Status.OK)
                .message("Найдено " + budgets.size() + " бюджетов")
                .json(Converter.toJson(budgets))
                .build();
    }

    @GetMapping(Mapping.Budget.user)
    public Response findByUserId(Request request) {
        ObjectMapper mapper = Converter.getInstance();
        Integer userId = null;
        try {
            JsonNode node = mapper.readTree(request.getJson());
            userId = node.get("id").asInt();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.IncorrectDataPassed;
        }
        List<Budget> budgets = budgetService.findbyUserId(userId);
        return Response.builder()
                .status(Response.Status.OK)
                .message("Найдено " + budgets.size() + " бюджетов")
                .build();

    }


}