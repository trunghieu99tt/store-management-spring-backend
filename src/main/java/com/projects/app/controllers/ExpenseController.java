package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.Revenue;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ExpenseDTO;
import com.projects.app.models.request.RevenueDTO;
import com.projects.app.services.ExpenseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/expense")
@Tag(name="expense")
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;
    @GetMapping("/")
    public ResponseEntity<APIResponse> getAll(

    ) throws BackendError {
        List<Expense> expense = expenseService.getAll();
        return ResponseTool.GET_OK(expense);

    }
    @PostMapping("/")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "Todo model to create", required = true,
                    schema =
                    @Schema(implementation = ExpenseDTO.class))
            @Valid @RequestBody ExpenseDTO expenseDTO)
            throws BackendError {
        Expense expense = expenseService.parseExpenseDTOToExpense(expenseDTO);
        expense.setDate(new Date());
        Expense newExpense = expenseService.createOne(expense);
        return ResponseTool.POST_OK(newExpense);
    }


}

