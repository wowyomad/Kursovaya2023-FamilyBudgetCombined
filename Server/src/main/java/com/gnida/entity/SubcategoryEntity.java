package com.gnida.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter @Setter @EqualsAndHashCode @ToString
@Table(name = "subcategory", schema = "budget_db")
public class SubcategoryEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subcategory_id", nullable = false)
    private int subcategoryId;

    @Basic
    @Column(name = "subcategory_name", nullable = false, length = 255)
    private String subcategoryName;

    @Basic
    @Column(name = "subcategory_value", nullable = false, precision = 2)
    private BigDecimal subcategoryValue;

    @Basic
    @Column(name = "category_id", nullable = false)
    private int categoryId;

}
