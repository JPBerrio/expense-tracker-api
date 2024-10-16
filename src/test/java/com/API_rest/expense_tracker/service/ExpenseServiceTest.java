
package com.API_rest.expense_tracker.service;
 
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.API_rest.expense_tracker.persistence.entities.ExpenseEntity;
import com.API_rest.expense_tracker.persistence.repositories.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllExpenses() {
        int page = 0;
        int elements = 10;
        Pageable pageable = PageRequest.of(page, elements);
        Page<ExpenseEntity> expectedPage = new PageImpl<>(Collections.emptyList());

        when(expenseRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<ExpenseEntity> result = expenseService.getAllExpenses(page, elements);

        assertEquals(expectedPage, result);
    }
}
