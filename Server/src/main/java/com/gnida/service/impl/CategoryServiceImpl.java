package com.gnida.service.impl;

import com.gnida.entity.Budget;
import com.gnida.entity.Category;
import com.gnida.model.Response;
import com.gnida.repository.CategoryRepository;
import com.gnida.service.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @NonNull CategoryRepository repository;

    @Override
    public Response findAllByBudget(Budget budget) {
        return Response.builder()
                .status(Response.Status.OK)
                .object(repository.findAllByBudgetId(budget.getId()))
                .build();
    }

    @Override
    public Response udpateCategory(Category category) {
        if (repository.findById(category.getId()) == null) {
            return Response.IncorrectDataPassed;
        }
        Category udpated = repository.save(category);
        return Response.builder()
                .status(Response.Status.OK)
                .object(udpated)
                .build();
    }

    @Override
    public Response addCategory(Category category) {
        if(repository.findById(category.getId()).isPresent()) {
            System.out.println("ввв");
            return Response.IncorrectDataPassed;
        }
        Category saved = repository.save(category);
        return Response.builder()
                .status(Response.Status.OK)
                .object(saved)
                .build();
    }

    @Override
    public Response deleteCategory(Category category) {
        repository.delete(category);
        return Response.builder()
                .status(Response.Status.OK)
                .build();
    }
}
