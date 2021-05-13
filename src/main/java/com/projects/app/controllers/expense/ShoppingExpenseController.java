package com.projects.app.controllers.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.expense.ShoppingExpense;
import com.projects.app.models.request.ServiceExpenseDTO;
import com.projects.app.models.request.ShoppingExpenseDTO;
import com.projects.app.services.expense.ExpenseService;
import com.projects.app.services.expense.ShoppingExpenseService;
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
@RequestMapping("api/v1/expense/shopping")
@Tag(name = "Shopping Expense")
public class ShoppingExpenseController {

    @Autowired
    ShoppingExpenseService shoppingExpenseService;

    @Autowired
    ExpenseService expenseService;

    @PostMapping("")
    public ResponseEntity<APIResponse> createOne(
            @Parameter(description = "Todo model to create", required = true,
                    schema =
                    @Schema(implementation = ServiceExpenseDTO.class))
            @Valid @RequestBody ShoppingExpenseDTO shoppingExpenseDTO)
            throws BackendError {
        ShoppingExpense shoppingExpense =
                shoppingExpenseService.parseExpenseServiceDTOToExpenseService(shoppingExpenseDTO);
        shoppingExpense.setDate(new Date());
        ShoppingExpense newShoppingExpense = shoppingExpenseService.createOne(shoppingExpense);
        return ResponseTool.POST_OK(newShoppingExpense);
    }

//    @ApiOperation(value = "update a expense")
//    @PutMapping("/{shoppingExpenseID}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<APIResponse> updateServiceExpense(
//            @PathVariable(name = "shoppingExpenseID") long shoppingExpenseID,
//            @Valid @RequestBody ShoppingExpenseDTO shoppingExpenseDTO
//    ) throws BackendError {
//        Expense expenseDB = expenseService.getOne(shoppingExpenseID);
//        if (expenseDB == null) {
//            String message = "Invalid expenseID ID";
//            throw new BackendError(HttpStatus.BAD_REQUEST, message);
//        }
//        ShoppingExpense shoppingExpense =
//                shoppingExpenseService.parseExpenseServiceDTOToExpenseService(shoppingExpenseDTO);
//        shoppingExpense.setDate(expenseDB.getDate());
//        shoppingExpense.setId(shoppingExpenseID);
////        return ResponseTool.PUT_OK(shoppingExpenseService.updateOne(shoppingExpense));
////    }
}
