package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIPagingResponse;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.FinancialPlan;
import com.projects.app.models.Report;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ExpenseDTO;
import com.projects.app.models.request.FinancialPlanDTO;
import com.projects.app.services.FinancialPlanService;
import com.projects.app.utils.Constant;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/financialPlans")
@Tag(name = "FinancialPlan")
public class FinancialPlanController {
    @Autowired
    private FinancialPlanService financialPlanService;

    @ApiOperation(value = "get generated financialFinancial")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIPagingResponse> getFinancialPlan(
        @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize,
        @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber
    ) throws BackendError {
        try {
            Page<FinancialPlan> financialPlans = financialPlanService.getAllFinancialPlan(pageNumber, pageSize);
            return ResponseTool.GET_OK(new ArrayList<Object>(financialPlans.getContent()), (int) financialPlans.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackendError(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

    @ApiOperation(value = "delete a financialPlan")
    @DeleteMapping("/{financialPlanID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> deleteFinancialPlan(
        @PathVariable(name = "financialPlanID") long financialPlanID
    ) throws BackendError {
        boolean ok = financialPlanService.deleteFinancialPlan(financialPlanID);
        if (ok) {
            return ResponseTool.DELETE_OK();
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "There is no financialPlan with this id");
        }
    }

    @PostMapping("")
    public ResponseEntity<APIResponse> createFinancialPlan(
        @Parameter(description = "Todo model to create", required = true,
            schema =
            @Schema(implementation = FinancialPlanDTO.class))
        @Valid @RequestBody FinancialPlanDTO financialPlanDTO)
        throws BackendError {
        FinancialPlan financialPlan = financialPlanService.parseFinancialPlanDTOToFinancialPlan(financialPlanDTO);
        FinancialPlan newFinancialPlan = financialPlanService.addFinancialPlan(financialPlan);
        return ResponseTool.POST_OK(newFinancialPlan);
    }

    @ApiOperation(value = "update a financialPlan")
    @PutMapping("/{financialPlanID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> updateFinancialPlan(
        @PathVariable(name = "financialPlanID") long financialPlanID,
        @Valid @RequestBody FinancialPlanDTO financialPlanDTO
    ) throws BackendError {
        FinancialPlan financialPlan = financialPlanService.parseFinancialPlanDTOToFinancialPlan(financialPlanDTO);
        financialPlan.setId(financialPlanID);
        FinancialPlan updatedFinancialPlan = financialPlanService.updateFinancePlan(financialPlan);
        return ResponseTool.PUT_OK(updatedFinancialPlan);
    }
}
