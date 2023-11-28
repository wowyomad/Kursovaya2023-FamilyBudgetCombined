package com.gnida.entity;

import com.gnida.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


@Entity
@Data
@Table(name = "category", schema = "budget_db")
public class Category implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id", nullable = false)
    private int id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType type;

    @Column(name = "budget_id", nullable = false)
    private int budgetId;

    @ManyToOne
    @JoinColumn(name = "parent_category", referencedColumnName = "category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    Set<Category> subcategories;
}
