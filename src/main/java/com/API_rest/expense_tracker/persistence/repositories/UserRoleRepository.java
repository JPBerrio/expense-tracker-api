package com.API_rest.expense_tracker.persistence.repositories;

import com.API_rest.expense_tracker.persistence.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
}
