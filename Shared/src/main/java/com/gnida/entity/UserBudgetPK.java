package com.gnida.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode @ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserBudgetPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    Budget budget;
}
