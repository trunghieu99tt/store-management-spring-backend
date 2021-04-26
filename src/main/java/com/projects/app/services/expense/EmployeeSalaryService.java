package com.projects.app.services.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.expense.EmployeeSalaryExpense;
import com.projects.app.models.request.EmployeeSalaryExpenseDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.StaffRepository;
import com.projects.app.repository.expense.EmployeeSalaryExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSalaryService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    EmployeeSalaryExpenseRepository employeeSalaryExpenseRepository;

    public EmployeeSalaryExpense createOne(EmployeeSalaryExpense ex) {
        return employeeSalaryExpenseRepository.save(ex);
    }

    public EmployeeSalaryExpense updateOne(EmployeeSalaryExpense ex) {
        return employeeSalaryExpenseRepository.save(ex);
    }

    public EmployeeSalaryExpense parseExpenseServiceDTOToExpenseService(EmployeeSalaryExpenseDTO employeeSalaryExpenseDTO) throws BackendError {
        EmployeeSalaryExpense employeeSalaryExpense = new EmployeeSalaryExpense();
        employeeSalaryExpense.setDate(employeeSalaryExpenseDTO.getDate());
        employeeSalaryExpense.setPaymentMethod(employeeSalaryExpenseDTO.getPaymentMethod());
        employeeSalaryExpense.setDescription(employeeSalaryExpenseDTO.getDescription());
        employeeSalaryExpense.setTotal(employeeSalaryExpenseDTO.getTotal());
        employeeSalaryExpense.setName(employeeSalaryExpenseDTO.getName());
        // TODO: Replace with actual employee ID
        employeeSalaryExpense.setEmployeeID(1L);
        List<BankAccount> bankAccount =
                bankAccountRepository.findBankAccountByAccountNumber(employeeSalaryExpenseDTO.getBankAccountNumber());
        if (bankAccount.size() > 0) {
            employeeSalaryExpense.setBankAccount(bankAccount.get(0));
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số tài khoản không đúng");
        }
        Optional<Staff> staff = staffRepository.findById(employeeSalaryExpenseDTO.getStaffID());
        if (staff.isPresent()) {
            employeeSalaryExpense.setStaff((Staff) staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid staff ID");
        }
        return employeeSalaryExpense;
    }
}
