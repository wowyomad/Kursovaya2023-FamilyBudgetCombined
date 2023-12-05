package com.gnida.service;

import com.gnida.entity.Budget;
import com.gnida.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction getById(Integer id);

    List<Transaction> getAllByUserId(Integer userId);

    List<Transaction> findAllByBudget(Budget budget);

    void save(Transaction transaction);
}
