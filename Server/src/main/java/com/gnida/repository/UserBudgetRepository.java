package com.gnida.repository;

import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.entity.UserBudget;
import com.gnida.entity.UserBudgetPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBudgetRepository extends JpaRepository<UserBudget, UserBudgetPK> {

    @Query("SELECT ub.id.budget FROM UserBudget ub WHERE ub.id.user = :userId")
    Budget findAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT ub.id.user FROM UserBudget ub WHERE ub.role = 'LEADER' AND ub.id.budget = :budgetId")
    User findLeaderByBudgetId(@Param("budgetId") Integer budgetId);
}
