package com.gnida.service.impl;

import com.gnida.entity.Budget;
import com.gnida.repository.BudgetRepository;
import com.gnida.service.BudgetService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    @NonNull
    BudgetRepository budgetRepository;

    public void delete(Budget entity) {
        budgetRepository.delete(entity);
    }

    @Override
    public List<Budget> findAllByUserId(Integer userId) {
       return budgetRepository.findAllByUserId(userId);
    }

    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    public Budget save(Budget entity) {
        return budgetRepository.save(entity);
    }

    public Budget findById(Integer integer) {
        return budgetRepository.findById(integer).orElseGet(() -> null);
    }


}
