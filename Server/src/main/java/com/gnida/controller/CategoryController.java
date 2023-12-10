package com.gnida.controller;

import com.gnida.entity.Budget;
import com.gnida.entity.Category;
import com.gnida.logging.LOGGER;
import com.gnida.mapping.DeleteMapping;
import com.gnida.mapping.GetMapping;
import com.gnida.mapping.PostMapping;
import com.gnida.mapping.UpdateMapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.service.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryController implements IController {

    @NonNull CategoryService categoryService;

    @GetMapping("/budget")
    Response finByBudgetId(Request request) {
        try {
            Budget budget = (Budget) request.getObject();
            return categoryService.findAllByBudget(budget);
        } catch (ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
    }

    @UpdateMapping("/category")
    Response updateCategory(Request request) {
        try {
            Category category = (Category) request.getObject();
            return categoryService.udpateCategory(category);
        } catch (ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
    }

    @PostMapping("/category")
    Response insertCategory(Request request) {
        try {
            System.out.println(request.getObject());
            Category category = (Category) request.getObject();
            return categoryService.addCategory(category);
        } catch (ClassCastException | NullPointerException e) {
            LOGGER.log(e.getMessage());
            return Response.IncorrectDataPassed;
        }
    }

    @DeleteMapping("/category")
    Response deleteCategory(Request request) {
        try {
            Category category = (Category) request.getObject();
            return categoryService.deleteCategory(category);
        } catch (ClassCastException | NullPointerException e) {
            return Response.IncorrectDataPassed;
        }
    }
}
