package com.gnida.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BudgetDto {
    String name;
    String leader;
    BigDecimal initialAmount;
    BigDecimal income;
    BigDecimal expenses;
    BigDecimal total;
    boolean  isFinished;
}
