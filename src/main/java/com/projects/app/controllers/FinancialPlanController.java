package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.Budget;
import com.projects.app.models.FinancialPlan;
import com.projects.app.models.request.BudgetDTO;
import com.projects.app.models.request.FinancialPlanDTO;
import com.projects.app.services.FinancialPlanService;
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
@RequestMapping("api/v1/financialplan")
@Tag(name = "financial plan")
public class FinancialPlanController {
    @Autowired
    FinancialPlanService financialPlanService;

    @GetMapping("")
    public ResponseEntity<APIResponse> getAll() {
        List<FinancialPlan> financialPlans = financialPlanService.getAll();
        return ResponseTool.GET_OK(financialPlans);
    }

    @GetMapping("/{financialPlanID}")
    public ResponseEntity<APIResponse> getFinancialPlanById(@PathVariable(name = "financialPlanID") long financialPlanId) throws BackendError {
        FinancialPlan financialPlan = financialPlanService.getOne(financialPlanId);
        if (financialPlan == null) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid financial plan ID");

        }
        return ResponseTool.GET_OK(financialPlan);
    }

    @PostMapping("")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "create", required = true,
                    schema = @Schema(implementation = FinancialPlanDTO.class))
            @Valid @RequestBody FinancialPlanDTO financialPlanDTO) throws BackendError {
        FinancialPlan financialPlan = financialPlanService.parseFinancialPlanDTOToFinancialPlan(financialPlanDTO);
        FinancialPlan newPlan = financialPlanService.createOne(financialPlan);
        return ResponseTool.POST_OK(newPlan);
    }

    @ApiOperation(value = "Update financial plam")
    @PutMapping("/{financialID}")
    public ResponseEntity<APIResponse> updatePlan(@PathVariable(name="financialID") long budgetID, @Valid @RequestBody FinancialPlanDTO financialPlanDTO) throws  BackendError{
        FinancialPlan financialPlan = financialPlanService.getOne(budgetID);
        if(financialPlan==null){
            throw new BackendError(HttpStatus.BAD_REQUEST,"Invalid ID");
        }
        FinancialPlan newPlan = financialPlanService.parseFinancialPlanDTOToFinancialPlan(financialPlanDTO);

        newPlan.setId(financialPlan.getId());//can't change ID
        return ResponseTool.PUT_OK(financialPlanService.updateOne(newPlan));
    }

    @ApiOperation(value = "delete a plan")
    @DeleteMapping("/{financialID}")

    public ResponseEntity<APIResponse> deletePlan(@PathVariable(name = "financialID") long financialPlanID) throws BackendError{
        boolean checkDelete = financialPlanService.deleteOne(financialPlanID);
        if(checkDelete){
            return ResponseTool.DELETE_OK();
        }else{
            throw new BackendError(HttpStatus.BAD_REQUEST,"Invalid ID");
        }
    }

}
