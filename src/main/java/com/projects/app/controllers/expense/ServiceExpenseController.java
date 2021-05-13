package com.projects.app.controllers.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.expense.ServiceExpense;
import com.projects.app.models.request.ServiceExpenseDTO;
import com.projects.app.services.expense.ExpenseService;
import com.projects.app.services.expense.ServiceExpenseService;
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

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/expense/service")
@Tag(name = "Service Expense")
public class ServiceExpenseController {

    @Autowired
    ServiceExpenseService serviceExpenseService;

    @Autowired
    ExpenseService expenseService;

    @PostMapping("")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "Todo model to create", required = true,
                    schema =
                    @Schema(implementation = ServiceExpenseDTO.class))
            @Valid @RequestBody ServiceExpenseDTO serviceExpenseDTO)
            throws BackendError {
        ServiceExpense serviceExpense = serviceExpenseService.parseExpenseServiceDTOToExpenseService(serviceExpenseDTO);
        serviceExpense.setDate(new Date());
        ServiceExpense newServiceExpense = serviceExpenseService.createOne(serviceExpense);
        return ResponseTool.POST_OK(newServiceExpense);
    }

    @ApiOperation(value = "update a expense")
    @PutMapping("/{serviceExpenseID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> updateServiceExpense(
            @PathVariable(name = "serviceExpenseID") long serviceExpenseID,
            @Valid @RequestBody ServiceExpenseDTO serviceExpenseDTO
    ) throws BackendError {
        Expense expenseDB = expenseService.getOne(serviceExpenseID);
        if (expenseDB == null) {
            String message = "Invalid expenseID ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        ServiceExpense serviceExpense = serviceExpenseService.parseExpenseServiceDTOToExpenseService(serviceExpenseDTO);
        serviceExpense.setDate(expenseDB.getDate());
        serviceExpense.setId(serviceExpenseID);
        return ResponseTool.PUT_OK(serviceExpenseService.updateOne(serviceExpense));
    }
}
