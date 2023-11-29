package com.gnida.service;

import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.entity.UserBudget;

import java.util.List;

public interface UserBudgetService {
    UserBudget save(UserBudget entity);
    UserBudget save(User user, Budget budget);
}
