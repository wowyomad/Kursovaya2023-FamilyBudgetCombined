package com.gnida.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction", schema = "budget_db")
public class TransactionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "date", nullable = false)
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id", nullable = false)
    private SubcategoryEntity subcategoryId;

    public SubcategoryEntity getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(SubcategoryEntity subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Basic
    @Column(name = "comment", nullable = true, length = 255)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",nullable = false)
    private UserEntity userId;

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "budget_id", referencedColumnName = "budget_id", nullable = false)
    private BudgetEntity budgetId;

    public BudgetEntity getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(BudgetEntity budgetId) {
        this.budgetId = budgetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return transactionId == that.transactionId  && subcategoryId == that.subcategoryId && userId == that.userId && budgetId == that.budgetId && Objects.equals(date, that.date) && Objects.equals(amount, that.amount) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, date, amount, subcategoryId, comment, userId, budgetId);
    }
}
