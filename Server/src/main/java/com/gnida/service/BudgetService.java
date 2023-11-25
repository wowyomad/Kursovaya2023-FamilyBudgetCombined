package com.gnida.service;

import com.gnida.entity.Budget;
import com.gnida.repository.BudgetRepository;

import java.util.List;

public class BudgetService {

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
