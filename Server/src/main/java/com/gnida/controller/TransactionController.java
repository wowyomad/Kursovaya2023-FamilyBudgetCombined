package com.gnida.controller;

import com.gnida.entity.Budget;
import com.gnida.entity.Transaction;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.mappings.Mapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransactionController implements IController {

    @NonNull
    TransactionService transactionService;

    @GetMapping(Mapping.Transaction.budget)
    Response findAllForBudget(Request request) {
        try {
            Budget budget = (Budget) request.getObject();
            List<Transaction> transactions = transactionService.findAllByBudget(budget);
            return Response.builder()
                    .object(transactions)
                    .status(Response.Status.OK)
                    .build();
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
            return Response.IncorrectDataPassed;
        }
    }

    @PostMapping(Mapping.Transaction.transaction)
    Response postTransaction(Request request) {
        try {
            Transaction transaction = (Transaction) request.getObject();
            Transaction savedTransaction = transactionService.save(transaction);
            return Response.builder()
                    .status(Response.Status.OK)
                    .object(savedTransaction)
                    .build();

        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
            return Response.IncorrectDataPassed;
        }
    }


}
