package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.Budget;
import com.projects.app.models.request.BudgetDTO;
import com.projects.app.services.BudgetService;
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
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("api/v1/budget")
@Tag(name = "budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    //get all budget
    @GetMapping("")
    public ResponseEntity<APIResponse> getAll() {
        List<Budget> budgets = budgetService.getAll();
        return ResponseTool.GET_OK(budgets);
    }

    //create a new budget
    @PostMapping("")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "create", required = true,
                    schema = @Schema(implementation = BudgetDTO.class))
            @Valid @RequestBody BudgetDTO budgetDTO) throws BackendError {
        Budget budget = budgetService.parseBudgetDTOToBudget(budgetDTO);
        Budget newBudget = budgetService.createOne(budget);
        return ResponseTool.POST_OK(newBudget);
    }

    // get one budget by id
    @GetMapping("/{budgetID}")
    public ResponseEntity<APIResponse> getBudgetById(@PathVariable(name = "budgetID") long budgetId) throws BackendError{
        Budget budget = budgetService.getOne(budgetId);
        if(budget==null){
            throw new BackendError(HttpStatus.BAD_REQUEST,"Invalid budget ID");
        }
        return ResponseTool.GET_OK(budget);
    }

    //update one budget by id
    @ApiOperation(value = "Update a Budget")
    @PutMapping("/{budgetID}")
    public ResponseEntity<APIResponse> updateBudget(@PathVariable(name="budgetID") long budgetID, @Valid @RequestBody BudgetDTO budgetDTO) throws  BackendError{
        Budget budget = budgetService.getOne(budgetID);
        if(budget==null){
            throw new BackendError(HttpStatus.BAD_REQUEST,"Invalid budget ID");
        }
        Budget newBudget = budgetService.parseBudgetDTOToBudget(budgetDTO);

        newBudget.setId(budget.getId());//can't change ID
        return ResponseTool.PUT_OK(budgetService.updateOne(newBudget));
    }

    //delete one budget by id
    @ApiOperation(value = "delete a budget")
    @DeleteMapping("/{budgetID}")

    public ResponseEntity<APIResponse> deleteBudget(@PathVariable(name = "budgetID") long budgetID) throws BackendError{
        boolean checkDelete = budgetService.deleteOne(budgetID);
        if(checkDelete){
            return ResponseTool.DELETE_OK();
        }else{
            throw new BackendError(HttpStatus.BAD_REQUEST,"Invalid budget");
        }
    }

}





