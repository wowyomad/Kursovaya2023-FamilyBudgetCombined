package com.gnida.service.impl;

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
    private TransactionRepository repository;

    @Override
    public Transaction getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Transaction> getByBudgetId(Integer id) {
        return repository.findByBudgetId(id);
    }

    @Override
    public List<Transaction> getAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId);
    }

}
