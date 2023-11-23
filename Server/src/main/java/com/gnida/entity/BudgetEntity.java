package com.gnida.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Getter @Setter @EqualsAndHashCode @ToString
@Table(name = "budget", schema = "budget_db")
public class BudgetEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "budget_id", nullable = false)
    private int budgetId;


    @Basic
    @Column(name = "budget_name", nullable = false, length = 255)
    private String budgetName;


    @Basic
    @Column(name = "initial_amount", nullable = false, precision = 2)
    private BigDecimal initialAmount;


    @Basic
    @Column(name = "expected_income", nullable = false, precision = 2)
    private BigDecimal expectedIncome;


    @Basic
    @Column(name = "expected_spending", nullable = false, precision = 2)
    private BigDecimal expectedSpending;


    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;


    @Basic
    @Column(name = "link", nullable = false, length = 36)
    private String link;


}
