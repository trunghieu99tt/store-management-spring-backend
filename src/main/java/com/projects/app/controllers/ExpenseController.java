package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ExpenseDTO;
import com.projects.app.services.expense.ExpenseService;
import io.swagger.annotations.ApiOperation;
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

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/expense")
@Tag(name = "expense")
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;

    @GetMapping("")
    public ResponseEntity<APIResponse> getAll() throws BackendError {
        List<Expense> expense = expenseService.getAll();
        return ResponseTool.GET_OK(expense);
    }

    @PostMapping("")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "create", required = true,
                    schema =
                    @Schema(implementation = ExpenseDTO.class))
            @Valid @RequestBody ExpenseDTO expenseDTO)
            throws BackendError {
        Expense expense = expenseService.parseExpenseDTOToExpense(expenseDTO);
        if (expenseDTO.getDate() != null) {
            expense.setDate(expenseDTO.getDate());
        } else {
            expense.setDate(new Date());
        }
        Expense newExpense = expenseService.createOne(expense);
        return ResponseTool.POST_OK(newExpense);
    }

    @GetMapping("/{expenseID}")
    public ResponseEntity<APIResponse> getExpense(@PathVariable(name = "expenseID") long expenseID) throws BackendError {
        Expense expense = expenseService.getOne(expenseID);
        if (expense == null) {
            String message = "Invalid revenue ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return ResponseTool.GET_OK(expense);
    }

    @ApiOperation(value = "update a expense")
    @PutMapping("/{expenseID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> updateRevenue(
            @PathVariable(name = "expenseID") long expenseID,
            @Valid @RequestBody ExpenseDTO expenseDTO
    ) throws BackendError {
        // Check if expense exists in database before handling update
        Expense expenseDB = expenseService.getOne(expenseID);
        if (expenseDB == null) {
            String message = "Invalid expenseID ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        Expense expense = expenseService.parseExpenseDTOToExpense(expenseDTO);
        // Set back id (these fields can't be changed)
        expense.setId(expenseID);
        return ResponseTool.PUT_OK(expenseService.updateOne(expense));
    }

    @ApiOperation(value = "delete a expense")
    @DeleteMapping("/{expenseID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> deleteRevenue(
            @PathVariable(name = "expenseID") long expenseID
    ) throws BackendError {
        boolean ok = expenseService.deleteOne(expenseID);
        if (ok) {
            return ResponseTool.DELETE_OK();
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "There is no expense  with this id");
        }
    }

    @ApiOperation(value = "Get statistic")
    @GetMapping("/statistic")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> getStatistic(
            @RequestParam Date dayStart,
            @RequestParam Date dayEnd
    ) throws BackendError {
        List<Expense> revenues = expenseService.getStatistic(dayStart, dayEnd);
        return ResponseTool.GET_OK(revenues);
    }
}

