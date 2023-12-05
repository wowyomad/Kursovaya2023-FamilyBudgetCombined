package com.gnida.repository;

import com.gnida.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUserId(Integer userId);

    List<Transaction> findAllByBudgetId(Integer id);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.category c WHERE c.type = 'INCOME' AND t.budget.id = :budgetId")
    BigDecimal getSumOfIncomes(@Param("budgetId") Integer budgetId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.category c WHERE c.type = 'EXPENSE' AND t.budget.id = :budgetId")
    BigDecimal getSumOfExpenses(@Param("budgetId") Integer budgetId);

    @Query("SELECT COALESCE(SUM(CASE WHEN c.type = 'INCOME' THEN t.amount ELSE -t.amount END), 0) FROM Transaction t JOIN t.category c WHERE t.budget.id = :budgetId")
    BigDecimal getResultForBudget(@Param("budgetId") Integer budgetId);

}
