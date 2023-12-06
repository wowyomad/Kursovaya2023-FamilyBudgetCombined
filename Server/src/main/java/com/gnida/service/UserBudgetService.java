package com.gnida.service;

import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.entity.UserBudget;

public interface UserBudgetService {
    UserBudget save(UserBudget entity);
    UserBudget save(User user, Budget budget);


    User findOwnerByBudgetId(Budget budget);
}
