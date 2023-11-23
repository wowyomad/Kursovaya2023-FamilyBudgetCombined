package com.gnida.entity;

import com.gnida.enums.CategoryType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter @EqualsAndHashCode @ToString
@Table(name = "category", schema = "budget_db")
public class CategoriesEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Basic
    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType categoryType;

    @Basic
    @Column(name = "budget_id", nullable = false)
    private int budgetId;

}
