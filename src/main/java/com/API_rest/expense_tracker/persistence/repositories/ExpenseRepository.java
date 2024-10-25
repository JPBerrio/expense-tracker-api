package com.API_rest.expense_tracker.persistence.repositories;

import com.API_rest.expense_tracker.persistence.entities.ExpenseEntity;
import com.API_rest.expense_tracker.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    Page<ExpenseEntity> findByUserEntityIdUserAndExpenseDateBetween(Long idUser, LocalDate starDate, LocalDate endDate, Pageable pageable);
    Page<ExpenseEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);
    List<ExpenseEntity> findByUserEntityIdUser(Long userId);
}
