package com.gnida.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_budget", schema = "budget_db")
@IdClass(UserBudgetEntityPK.class)
public class UserBudgetEntity {
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
        UserBudgetEntity that = (UserBudgetEntity) o;
        return userId == that.userId && budgetId == that.budgetId && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, budgetId, role);
    }
}
