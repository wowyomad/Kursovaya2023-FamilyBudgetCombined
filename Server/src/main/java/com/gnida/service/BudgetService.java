package com.gnida.service;

import com.gnida.entity.Budget;
import com.gnida.repository.BudgetRepository;

import java.util.List;

public interface BudgetService {
    public List<Budget> findAll();

    public Budget save(Budget entity);

    public Budget findById(Integer integer);
    public void delete(Budget entity);
}
