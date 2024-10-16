package com.API_rest.expense_tracker.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expense", nullable = false, unique = true)
    private Long idExpense;

    @Column(name = "expense_name", length = 50)
    private String expenseName;

    @Column(name = "expenditure_amount", nullable = false, columnDefinition = "Decimal(10,2)")
    private Double expenditureAmount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private ExpenseCategoryEntity expenseCategoryEntity;
}
