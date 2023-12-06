package com.gnida.service.impl;

import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.repository.BudgetRepository;
import com.gnida.service.BudgetService;
import com.gnida.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    @NonNull
    BudgetRepository budgetRepository;

    @NonNull UserService userService;

    public void delete(Budget entity) {
        budgetRepository.delete(entity);
    }

    @Override
    public List<Budget> findAllByUserId(Integer userId) {
        return  budgetRepository.findAllByUserId(userId);
    }


    @Override
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    @Override
    public Budget save(Budget entity) {
        User owner  = userService.save(entity.getOwner());
        System.out.println(owner);
        entity.setOwner(owner);
        return budgetRepository.save(entity);
    }

    @Override
    public Optional<Budget> update(Budget entity) {
        if(budgetRepository.existsById(entity.getId())) {
            return Optional.of(budgetRepository.save(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Budget findById(Integer integer) {
        return budgetRepository.findById(integer).orElseGet(() -> null);
    }


}
