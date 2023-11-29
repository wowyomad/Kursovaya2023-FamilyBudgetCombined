package com.gnida.repository;

import com.gnida.entity.UserBudget;
import com.gnida.entity.UserBudgetPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBudgetRepository extends JpaRepository<UserBudget, UserBudgetPK> {

}
