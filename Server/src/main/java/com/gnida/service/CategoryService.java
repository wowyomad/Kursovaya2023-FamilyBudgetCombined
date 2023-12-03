package com.gnida.service;

import com.gnida.entity.Budget;
import com.gnida.entity.Category;
import com.gnida.model.Response;

public interface CategoryService {

    Response findAllByBudget(Budget budget);

    Response udpateCategory(Category category);

    Response addCategory(Category category);

    Response deleteCategory(Category category);
}
