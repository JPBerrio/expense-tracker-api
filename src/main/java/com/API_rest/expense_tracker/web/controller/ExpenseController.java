package com.API_rest.expense_tracker.web.controller;

import com.API_rest.expense_tracker.persistence.entities.ExpenseEntity;
import com.API_rest.expense_tracker.service.ExpenseService;
import com.API_rest.expense_tracker.web.dto.ExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseEntity>> getAllExpenses(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int elements) {
        return ResponseEntity.ok(expenseService.getAllExpenses(page, elements));
    }

    @GetMapping("/users/{idUser}/last-week")
    public ResponseEntity<List<ExpenseEntity>> getExpensesForLastWeek(@PathVariable Long idUser) {
        List<ExpenseEntity> expenses = expenseService.getExpenseForLastWeek(idUser);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/users/{idUser}/last-month")
    public ResponseEntity<List<ExpenseEntity>> getExpensesForLastMonth(@PathVariable Long idUser) {
        List<ExpenseEntity> expenses = expenseService.getExpenseForLastMonth(idUser);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/users/{idUser}/last-three-months")
    public ResponseEntity<List<ExpenseEntity>> getExpensesForLastThreeMonths(@PathVariable Long idUser) {
        List<ExpenseEntity> expenses = expenseService.getExpenseForLastThreeMonths(idUser);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/users/{idUser}/filter")
    public ResponseEntity<List<ExpenseEntity>> getExpensesByDateRange(@PathVariable Long idUser,
                                                                      @RequestParam("startDate") LocalDate startDate,
                                                                      @RequestParam("endDate") LocalDate endDate) {
        List<ExpenseEntity> expenses = expenseService.getExpensesByDateRange(idUser, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<ExpenseEntity> saveExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseEntity createdExpense = expenseService.createExpense(expenseDTO);
        return ResponseEntity.ok(createdExpense);
    }

    @PutMapping("/{idExpense}")
    public ResponseEntity<ExpenseEntity> updateExpense(@PathVariable Long idExpense,
                                                       @RequestBody ExpenseDTO expenseDTO) {
        ExpenseEntity updateExpense = expenseService.updateExpense(idExpense, expenseDTO);
        return ResponseEntity.ok(updateExpense);
    }

    @DeleteMapping("/{idExpense}")
    public ResponseEntity<Void> deleteExpense(@PathVariable long idExpense) {
        if (expenseService.exists(idExpense)) {
            expenseService.deleteExpense(idExpense);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
