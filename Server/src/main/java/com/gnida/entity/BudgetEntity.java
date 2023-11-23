package com.gnida.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Table(name = "budget", schema = "budget_db")
public class BudgetEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "budget_id", nullable = false)
    private int budgetId;

    @Column(name = "budget_name", nullable = false)
    private String budgetName;

    @ColumnDefault("0")
    @Column(name = "initial_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal initialAmount;

    @ColumnDefault("0")
    @Column(name = "expected_income", nullable = false, precision = 14, scale = 2)
    private BigDecimal expectedIncome;

    @ColumnDefault("0")
    @Column(name = "expected_spending", nullable = false, precision = 14, scale = 2)
    private BigDecimal expectedSpending;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "link", nullable = false, length = 36)
    private String link;


}
