package com.gnida.repository;

import com.gnida.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByBudgetId(Integer budgetId);
}
