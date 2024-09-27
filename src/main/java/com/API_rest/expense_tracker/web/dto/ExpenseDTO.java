package com.API_rest.expense_tracker.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long idExpense;
    private String expenseName;
    private Double expenditureAmount;
    private Long userId;
    private Integer idCategory;
}
