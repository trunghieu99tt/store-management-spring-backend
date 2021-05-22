package com.projects.app.controllers.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.expense.EmployeeSalaryExpense;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.EmployeeSalaryExpenseDTO;
import com.projects.app.services.expense.EmployeeSalaryService;
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

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/expense/employeeSalary")
@Tag(name = "Employee Salary Expense")
public class EmployeeSalaryExpenseController {

    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @Autowired
    ExpenseService expenseService;

    @PostMapping("")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "employeeSalaryExpenseDTO to be created", required = true,
                    schema =
                    @Schema(implementation = EmployeeSalaryExpenseDTO.class))
            @Valid @RequestBody EmployeeSalaryExpenseDTO employeeSalaryExpenseDTO)
            throws BackendError {
        EmployeeSalaryExpense employeeSalaryExpense =
                employeeSalaryService.parseExpenseServiceDTOToExpenseService(employeeSalaryExpenseDTO);
        EmployeeSalaryExpense newEmployeeSalaryExpense = employeeSalaryService.createOne(employeeSalaryExpense);
        return ResponseTool.POST_OK(newEmployeeSalaryExpense);
    }

    @ApiOperation(value = "update a employee salary expense")
    @PutMapping("/{employeeSalaryExpenseID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> updateServiceExpense(
            @PathVariable(name = "employeeSalaryExpenseID") long employeeSalaryExpenseID,
            @Valid @RequestBody EmployeeSalaryExpenseDTO employeeSalaryExpenseDTO
    ) throws BackendError {
        Expense expenseDB = expenseService.getOne(employeeSalaryExpenseID);
        if (expenseDB == null) {
            String message = "Invalid expenseID ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        EmployeeSalaryExpense employeeSalaryExpense =
                employeeSalaryService.parseExpenseServiceDTOToExpenseService(employeeSalaryExpenseDTO);
        employeeSalaryExpense.setId(employeeSalaryExpenseID);
        return ResponseTool.PUT_OK(employeeSalaryService.updateOne(employeeSalaryExpense));
    }
}
