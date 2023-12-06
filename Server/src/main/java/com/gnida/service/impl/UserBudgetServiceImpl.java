package com.gnida.service.impl;

import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.entity.UserBudget;
import com.gnida.entity.UserBudgetPK;
import com.gnida.enums.UserBudgetRole;
import com.gnida.repository.UserBudgetRepository;
import com.gnida.service.UserBudgetService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserBudgetServiceImpl implements UserBudgetService {
    @NonNull UserBudgetRepository repository;


    @Override
    public UserBudget save(UserBudget entity) {
        return repository.save(entity);
    }

    @Override
    public UserBudget save(User user, Budget budget) {
        UserBudget userBudget = new UserBudget();
        userBudget.setId(new UserBudgetPK(user, budget));
        userBudget.setRole(UserBudgetRole.LEADER);
        return repository.save(userBudget);
    }

    @Override
    public User findOwnerByBudgetId(Budget budget) {
        return repository.findLeaderByBudgetId(budget) ;
    }

}
