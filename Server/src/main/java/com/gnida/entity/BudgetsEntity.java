package com.gnida.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "budgets", schema = "budget_db")
public class BudgetsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "budget_id", nullable = false)
    private int budgetId;

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    @Basic
    @Column(name = "budget_name", nullable = false, length = 255)
    private String budgetName;

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    @Basic
    @Column(name = "initial_amount", nullable = false, precision = 2)
    private BigDecimal initialAmount;

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    @Basic
    @Column(name = "expected_income", nullable = false, precision = 2)
    private BigDecimal expectedIncome;

    public BigDecimal getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(BigDecimal expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    @Basic
    @Column(name = "expected_spending", nullable = false, precision = 2)
    private BigDecimal expectedSpending;

    public BigDecimal getExpectedSpending() {
        return expectedSpending;
    }

    public void setExpectedSpending(BigDecimal expectedSpending) {
        this.expectedSpending = expectedSpending;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "link", nullable = false, length = 36)
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetsEntity that = (BudgetsEntity) o;
        return budgetId == that.budgetId && Objects.equals(budgetName, that.budgetName) && Objects.equals(initialAmount, that.initialAmount) && Objects.equals(expectedIncome, that.expectedIncome) && Objects.equals(expectedSpending, that.expectedSpending) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, budgetName, initialAmount, expectedIncome, expectedSpending, startDate, endDate, link);
    }
}
