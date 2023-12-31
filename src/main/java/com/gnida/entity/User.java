package com.gnida.entity;

import com.gnida.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "user", schema = "budget_db")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role = UserRole.USER;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInfo info;

}
