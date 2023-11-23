package com.gnida.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter @EqualsAndHashCode @ToString
public class UserBudgetEntityPK implements Serializable {
    private int userId;
    private int budgetId;

}
