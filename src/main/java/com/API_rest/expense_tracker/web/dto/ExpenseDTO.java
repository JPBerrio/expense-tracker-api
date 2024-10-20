package com.API_rest.expense_tracker.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private String expenseName;
    private Double expenditureAmount;
    private Long userId;
    private ExpenseCategoryDTO expenseCategoryEntity;
    private LocalDate expenseDate;
}
