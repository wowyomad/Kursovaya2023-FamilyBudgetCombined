package com.gnida.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Table(name = "transaction", schema = "budget_db")
public class TransactionEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;


    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id", nullable = false)
    private SubcategoryEntity subcategoryId;


    @ColumnDefault("")
    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",nullable = false)
    private UserEntity user;


    @ManyToOne
    @JoinColumn(name = "budget_id", referencedColumnName = "budget_id", nullable = false)
    private BudgetEntity budget;


}
