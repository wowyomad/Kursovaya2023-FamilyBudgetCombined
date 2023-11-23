package com.gnida.entity;

import com.gnida.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "user", schema = "budget_db")
public class UserEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole = UserRole.USER;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

}
