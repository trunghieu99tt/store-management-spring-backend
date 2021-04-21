package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.Revenue;
import com.projects.app.models.request.RevenueDTO;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.UserRepository;
import com.projects.app.services.RevenueService;
import com.projects.app.utils.Constant;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/revenue")
@RequiredArgsConstructor
@Tag(name = "revenue")
public class RevenueController {


    @Autowired
    RevenueService revenueService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;


    @ApiOperation(value = "Get revenues")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> getRevenues(
            @RequestParam(required = false, defaultValue = "-1") long bankAccountID,
            @RequestParam(required = false) Date day,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber
    ) throws BackendError {
        try {
            Page<Revenue> revenues = revenueService.getAllRevenue(bankAccountID, day, pageNumber, pageSize);
            return ResponseTool.GET_OK(revenues.getContent());
        } catch (Exception e) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid bank account ID");
        }
    }

    @ApiOperation(value = "Get revenue by id")
    @GetMapping("/{revenueID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> getRevenue(
            @PathVariable(name = "revenueID") long revenueID
    ) throws BackendError {

        Revenue revenue = revenueService.getRevenue(revenueID);

        if (revenue == null) {
            String message = "Invalid revenue ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return ResponseTool.GET_OK(revenue);

    }

    @ApiOperation(value = "update a revenue")
    @PutMapping("/{revenueID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> updateRevenue(
            @PathVariable(name = "revenueID") long revenueID,
            @Valid @RequestBody RevenueDTO revenueDTO
    ) throws BackendError {
        Revenue revenueDB = revenueService.getRevenue(revenueID);
        if (revenueDB == null) {
            String message = "Invalid revenue ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        Revenue revenue = revenueService.parseRevenueDTOToRevenue(revenueDTO);
        revenue.setCreatedAt(revenueDB.getCreatedAt());
        revenue.setId(revenueID);
        return ResponseTool.PUT_OK(revenueService.updateRevenue(revenue));
    }

    @ApiOperation(value = "delete a revenue")
    @DeleteMapping("/{revenueID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> deleteRevenue(
            @PathVariable(name = "revenueID") long revenueID
    ) throws BackendError {
        boolean ok = revenueService.deleteRevenue(revenueID);
        if (ok) {
            return ResponseTool.DELETE_OK();
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "There is no revenue item with this id");
        }
    }


    @ApiOperation(value = "Create revenue")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<APIResponse> createTodo(
            @Parameter(description = "Todo model to create", required = true,
                    schema =
                    @Schema(implementation = RevenueDTO.class))
            @Valid @RequestBody RevenueDTO revenueDTO)
            throws BackendError {
        Revenue revenue = revenueService.parseRevenueDTOToRevenue(revenueDTO);
        revenue.setCreatedAt(new Date());
        Revenue newRevenue = revenueService.add(revenue);
        return ResponseTool.POST_OK(newRevenue);
    }
}
