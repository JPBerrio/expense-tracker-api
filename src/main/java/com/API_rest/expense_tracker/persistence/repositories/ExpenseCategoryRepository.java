package com.API_rest.expense_tracker.persistence.repositories;

import com.API_rest.expense_tracker.persistence.entities.ExpenseCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Integer> {
}
