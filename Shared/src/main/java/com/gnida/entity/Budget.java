package com.gnida.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Data
@Table(name = "budget", schema = "budget_db")
public class Budget implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "budget_id", nullable = false)
    private int id;

    @Column(name = "budget_name", nullable = false)
    private String name;

    @ColumnDefault("0")
    @Column(name = "initial_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal initialAmount;

    @ColumnDefault("0")
    @Column(name = "expected_income", nullable = false, precision = 14, scale = 2)
    private BigDecimal expectedIncome;

    @ColumnDefault("0")
    @Column(name = "expected_spending", nullable = false, precision = 14, scale = 2)
    private BigDecimal expectedExpense;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @Column(name = "link", unique = true, length = 16)
    private String link = "1";

    @PrePersist
    protected void onCreate() {
        link = String.valueOf(Long.valueOf((new Random().nextLong(0, 100000))));
        startDate = LocalDateTime.now();
        endDate = startDate.plusMonths(1);
    }

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User owner;
}
