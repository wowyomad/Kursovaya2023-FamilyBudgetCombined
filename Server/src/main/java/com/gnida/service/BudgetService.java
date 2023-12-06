package com.gnida.service;

import com.gnida.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetService {
    public List<Budget> findAll();
    public Budget save(Budget entity);

    Optional<Budget> update(Budget entity);

    public Budget findById(Integer integer);
    public void delete(Budget entity);

    List<Budget> findAllByUserId(Integer userId);
}
