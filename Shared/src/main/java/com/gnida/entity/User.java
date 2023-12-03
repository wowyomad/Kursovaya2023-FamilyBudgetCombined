package com.gnida.entity;

import com.gnida.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.DefaultCall;

import java.io.Serializable;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "budget_db")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @ColumnDefault(value = "'USER'")
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role = UserRole.USER;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @ColumnDefault("true")
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInfo info;

}


