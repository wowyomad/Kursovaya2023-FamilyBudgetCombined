package com.gnida.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "subcategory", schema = "budget_db")
public class SubcategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subcategory_id", nullable = false)
    private int subcategoryId;

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Basic
    @Column(name = "subcategory_name", nullable = false, length = 255)
    private String subcategoryName;

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Basic
    @Column(name = "subcategory_value", nullable = false, precision = 2)
    private BigDecimal subcategoryValue;

    public BigDecimal getSubcategoryValue() {
        return subcategoryValue;
    }

    public void setSubcategoryValue(BigDecimal subcategoryValue) {
        this.subcategoryValue = subcategoryValue;
    }

    @Basic
    @Column(name = "category_id", nullable = false)
    private int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcategoryEntity that = (SubcategoryEntity) o;
        return subcategoryId == that.subcategoryId && categoryId == that.categoryId && Objects.equals(subcategoryName, that.subcategoryName) && Objects.equals(subcategoryValue, that.subcategoryValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subcategoryId, subcategoryName, subcategoryValue, categoryId);
    }
}
