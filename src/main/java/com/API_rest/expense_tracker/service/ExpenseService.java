package com.API_rest.expense_tracker.service;

import com.API_rest.expense_tracker.persistence.entities.ExpenseCategoryEntity;
import com.API_rest.expense_tracker.persistence.entities.ExpenseEntity;
import com.API_rest.expense_tracker.persistence.entities.UserEntity;
import com.API_rest.expense_tracker.persistence.repositories.ExpenseCategoryRepository;
import com.API_rest.expense_tracker.persistence.repositories.ExpenseRepository;
import com.API_rest.expense_tracker.persistence.repositories.UserRepository;
import com.API_rest.expense_tracker.web.dto.ExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }


    public Page<ExpenseEntity> getAllExpenses(int page, int elements) {
        Pageable pageable = PageRequest.of(page, elements);
        return expenseRepository.findAll(pageable);
    }

    public Page<ExpenseEntity> getExpensesByUser(Long idUser, int page, int elements) {
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.findByUserEntity(user, PageRequest.of(page, elements));
    }

    @Transactional
    public ExpenseEntity createExpense(ExpenseDTO expenseDTO) {
        UserEntity user = userRepository.findById(expenseDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExpenseCategoryEntity category = expenseCategoryRepository.findById(expenseDTO.getExpenseCategoryEntity().getIdCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ExpenseEntity expenseEntity = ExpenseEntity.builder()
                .expenseName(expenseDTO.getExpenseName())
                .expenditureAmount(expenseDTO.getExpenditureAmount())
                .userEntity(user)
                .expenseCategoryEntity(category)
                .expenseDate(LocalDate.now())
                .build();

        return expenseRepository.save(expenseEntity);
    }

    @Transactional
    public ExpenseEntity updateExpense(Long idExpense, ExpenseDTO expenseDTO) {
        ExpenseEntity existingExpense = expenseRepository.findById(idExpense)
                .orElseThrow(()-> new RuntimeException("Expense not found"));

        existingExpense.setExpenseName(expenseDTO.getExpenseName());
        existingExpense.setExpenditureAmount(expenseDTO.getExpenditureAmount());
        existingExpense.setExpenseDate(expenseDTO.getExpenseDate());

        if (expenseDTO.getExpenseCategoryEntity() != null) {
            ExpenseCategoryEntity category = expenseCategoryRepository.findById(expenseDTO.getExpenseCategoryEntity().getIdCategory())
                    .orElseThrow(()-> new RuntimeException("Category not found"));
            existingExpense.setExpenseCategoryEntity(category);
        }

        return expenseRepository.save(existingExpense);
    }

    public boolean exists(long idExpense) {
        return expenseRepository.existsById(idExpense);
    }

    public List<ExpenseEntity> getExpenseForLastWeek(Long idUser) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        return expenseRepository.findByUserEntityIdUserAndExpenseDateBetween(idUser, startDate, endDate);
    }

    public List<ExpenseEntity> getExpenseForLastMonth(Long idUser) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        return expenseRepository.findByUserEntityIdUserAndExpenseDateBetween(idUser, startDate, endDate);
    }

    public List<ExpenseEntity> getExpenseForLastThreeMonths(Long idUser) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(3);
        return expenseRepository.findByUserEntityIdUserAndExpenseDateBetween(idUser, startDate, endDate);
    }

    public List<ExpenseEntity> getExpensesByDateRange(Long idUser, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserEntityIdUserAndExpenseDateBetween(idUser, startDate, endDate);
    }

    @Transactional
    public void deleteExpense(long idExpense) {
        if (!expenseRepository.existsById(idExpense)) {
            throw new RuntimeException("Expense not found");
        }
        expenseRepository.deleteById(idExpense);
    }
}
