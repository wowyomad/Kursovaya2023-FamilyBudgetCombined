package com.gnida.repository;

import com.gnida.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    @Query("SELECT ub.id.budget FROM UserBudget ub WHERE ub.id.user.id = :userId")
    List<Budget> findAllByUserId(Integer userId);

}
