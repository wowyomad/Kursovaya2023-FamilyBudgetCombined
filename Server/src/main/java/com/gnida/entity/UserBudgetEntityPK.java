package com.gnida.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode @ToString
@Data
@Embeddable
public class UserBudgetEntityPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    BudgetEntity budget;
}
