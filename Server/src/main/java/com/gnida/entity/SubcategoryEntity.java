package com.gnida.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "subcategory", schema = "budget_db")
public class SubcategoryEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subcategory_id", nullable = false)
    private int subcategoryId;

    @Column(name = "subcategory_name", nullable = false)
    private String subcategoryName;

    @Column(name = "subcategory_value", nullable = false, precision = 14, scale = 2)
    private BigDecimal subcategoryValue = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

}
