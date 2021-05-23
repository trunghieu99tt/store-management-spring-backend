package com.projects.app.services.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.expense.ShoppingExpense;
import com.projects.app.models.request.ShoppingExpenseDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.expense.ShoppingExpenseRepository;
import com.projects.app.repository.user.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingExpenseService {
    @Autowired
    ShoppingExpenseRepository shoppingExpenseRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    StaffRepository staffRepository;

    public ShoppingExpense createOne(ShoppingExpense ex) {
        return shoppingExpenseRepository.save(ex);
    }

    public ShoppingExpense updateOne(ShoppingExpense ex) {
        return shoppingExpenseRepository.save(ex);
    }

    public ShoppingExpense parseExpenseServiceDTOToExpenseService(ShoppingExpenseDTO shoppingExpenseDTO) throws BackendError {
        ShoppingExpense shoppingExpense = new ShoppingExpense();
        shoppingExpense.setDate(shoppingExpenseDTO.getDate());
        shoppingExpense.setPaymentMethod(shoppingExpenseDTO.getPaymentMethod());
        shoppingExpense.setDescription(shoppingExpenseDTO.getDescription());
        shoppingExpense.setTotal(shoppingExpenseDTO.getTotal());
        shoppingExpense.setName(shoppingExpenseDTO.getName());
        shoppingExpense.setPriceUnit(shoppingExpenseDTO.getPriceUnit());
        shoppingExpense.setProductID(shoppingExpenseDTO.getProductID());
        shoppingExpense.setQuantity(shoppingExpenseDTO.getQuantity());
        List<BankAccount> bankAccount =
                bankAccountRepository.findBankAccountByAccountNumber(shoppingExpenseDTO.getBankAccountNumber());
        if (bankAccount.size() > 0) {
            shoppingExpense.setBankAccount(bankAccount.get(0));
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số tài khoản không đúng");
        }

        Optional<Staff> staff = staffRepository.findById(shoppingExpenseDTO.getStaffID());
        if (staff.isPresent()) {
            shoppingExpense.setStaff(staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid staff ID");
        }
        return shoppingExpense;
    }

}
