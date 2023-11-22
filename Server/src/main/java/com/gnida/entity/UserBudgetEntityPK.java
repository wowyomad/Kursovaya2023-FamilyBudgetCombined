package com.gnida.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserBudgetEntityPK implements Serializable {
    private int userId;
    private int budgetId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBudgetEntityPK that = (UserBudgetEntityPK) o;
        return userId == that.userId && budgetId == that.budgetId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, budgetId);
    }
}
