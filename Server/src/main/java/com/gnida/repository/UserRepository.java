package com.gnida.repository;

import com.gnida.entity.User;
import com.gnida.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findByIdAndIsActive(Integer id, boolean isActive);

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    boolean existsByRole(UserRole role);
}
