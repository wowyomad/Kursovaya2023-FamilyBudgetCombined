package com.gnida.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BudgetDto {
    String name;
    String login;
    BigDecimal initialAmount;
    BigDecimal income;
    BigDecimal expenses;
    LocalDateTime dateStarted;
    LocalDateTime dateFinished;
}
