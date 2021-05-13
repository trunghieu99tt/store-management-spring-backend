package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIPagingResponse;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.FinancialPlan;
import com.projects.app.services.FinancialPlanService;
import com.projects.app.utils.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/plans")
public class CapitalInvestmentPlanController {

    @Autowired
    FinancialPlanService financialPlanService;

    @ApiOperation(value = "Get financial plans")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIPagingResponse> getInvestmentPlan(
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber
    ) throws BackendError {
        try {
            Page<FinancialPlan> financialPlans = financialPlanService.getAllFinancialPlan(pageNumber, pageSize);
            System.out.println(financialPlans);
            return ResponseTool.GET_OK(Collections.singletonList(new ArrayList<>(financialPlans.getContent())), (int) financialPlans.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackendError(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @GetMapping("/{planId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> getInvestmentPlanById(
            @PathVariable(name = "planId") long planId
    ) throws BackendError {
        try {
            FinancialPlan financialPlan = financialPlanService.getFinancialPlan(planId);
            return ResponseTool.GET_OK(financialPlan);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid id");
        }
    }

    @ApiOperation(value = "Create new financial plan")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public ResponseEntity<APIResponse> createFinancialPlan(
            @Valid @RequestBody FinancialPlan financialPlan
    ) throws BackendError {
        FinancialPlan newFinancialPlan = financialPlanService.addOrUpdateFinancialPlan(financialPlan);
        return ResponseTool.POST_OK(newFinancialPlan);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> updateFinancialPlan(
            @Valid @RequestBody FinancialPlan financialPlan
    ) throws BackendError {
        FinancialPlan newFinancialPlan = financialPlanService.addOrUpdateFinancialPlan(financialPlan);
        return ResponseTool.PUT_OK(newFinancialPlan);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<APIResponse> deleteFinancialPlan(
        @PathVariable(name = "planId") Long planId
    ) throws BackendError{
        boolean isOk = financialPlanService.deleteFinancialPlan(planId);
        if (!isOk){
            throw new BackendError(HttpStatus.BAD_REQUEST, "Id not exist!.");
        }
        return ResponseTool.DELETE_OK();
    }

}
