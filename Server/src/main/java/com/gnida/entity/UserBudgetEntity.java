package com.gnida.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter @EqualsAndHashCode @ToString
@Table(name = "user_budget", schema = "budget_db")
@IdClass(UserBudgetEntityPK.class)
public class UserBudgetEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private BudgetEntity budget;

    @Column(name = "role", nullable = false, length = 255)
    private String role;

}
