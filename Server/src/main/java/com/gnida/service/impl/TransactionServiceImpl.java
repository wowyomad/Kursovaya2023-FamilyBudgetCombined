package com.gnida.service.impl;

import com.gnida.entity.Budget;
import com.gnida.entity.Transaction;
import com.gnida.repository.TransactionRepository;
import com.gnida.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @NonNull
    private TransactionRepository transactionRepository;

    @Override
    public Transaction getById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> getByBudgetId(Integer id) {
        return transactionRepository.findAllByBudgetId(id);
    }

    @Override
    public List<Transaction> getAllByUserId(Integer userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    @Override
    public List<Transaction> findAllByBudget(Budget budget) {
        return transactionRepository.findAllByBudgetId(budget.getId());
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
