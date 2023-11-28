package com.gnida.service.impl;

import com.gnida.entity.Budget;
import com.gnida.repository.BudgetRepository;
import com.gnida.service.BudgetService;

import java.util.List;

public class BudgetServiceImpl implements BudgetService {
    BudgetRepository repository;

    public void delete(Budget entity) {
        repository.delete(entity);
    }
    public List<Budget> findAll() {
        return repository.findAll();
    }

    public Budget save(Budget entity) {
        return repository.save(entity);
    }

    public Budget findById(Integer integer) {
        return repository.findById(integer).orElseGet(() -> null);
    }
}
