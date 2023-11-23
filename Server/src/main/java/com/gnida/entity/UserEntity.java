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
@Table(name = "user", schema = "budget_db")
public class UserEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;


    @Basic
    @Column(name = "login", nullable = false, length = 255)
    private String login;

    @Basic
    @Column(name = "password", nullable = false, length = 60)
    private String password;

}
