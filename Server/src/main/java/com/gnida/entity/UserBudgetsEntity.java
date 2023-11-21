package com.gnida.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_budgets", schema = "budget_db")
@IdClass(com.gnida.entity.UserBudgetsEntityPK.class)
public class UserBudgetsEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Id
    @Column(name = "budget_id", nullable = false)
    private int budgetId;

    @Column(name = "role", nullable = false, length = 255)
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBudgetsEntity that = (UserBudgetsEntity) o;
        return userId == that.userId && budgetId == that.budgetId && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, budgetId, role);
    }
}
