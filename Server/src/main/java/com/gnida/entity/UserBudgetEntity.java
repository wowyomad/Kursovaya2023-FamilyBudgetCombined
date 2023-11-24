package com.gnida.entity;

import com.gnida.enums.UserBudgetRole;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "user_budget", schema = "budget_db")
public class UserBudgetEntity implements Serializable {
    @EmbeddedId
    UserBudgetEntityPK id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserBudgetRole role;
}
