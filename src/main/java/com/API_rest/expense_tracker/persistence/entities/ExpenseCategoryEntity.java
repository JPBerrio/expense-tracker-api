package com.API_rest.expense_tracker.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expense_categories")
public class ExpenseCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", nullable = false)
    private Integer idCategory;

    @Column(name = "name_category", nullable = false, unique = true)
    private String nameCategory;

    @OneToMany(mappedBy = "expenseCategoryEntity")
    @JsonIgnore
    private List<ExpenseEntity> expenses;
}
