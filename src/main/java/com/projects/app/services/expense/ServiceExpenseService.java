package com.projects.app.services.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.expense.ServiceExpense;
import com.projects.app.models.request.ServiceExpenseDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.StaffRepository;
import com.projects.app.repository.expense.ServiceExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceExpenseService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ServiceExpenseRepository serviceExpenseRepository;

    public ServiceExpense createOne(ServiceExpense ex) {
        return serviceExpenseRepository.save(ex);
    }

    public ServiceExpense updateOne(ServiceExpense ex) {
        return serviceExpenseRepository.save(ex);
    }

    public ServiceExpense parseExpenseServiceDTOToExpenseService(ServiceExpenseDTO serviceExpenseDTO) throws BackendError {
        ServiceExpense serviceExpense = new ServiceExpense();
        serviceExpense.setDate(serviceExpenseDTO.getDate());
        serviceExpense.setPaymentMethod(serviceExpenseDTO.getPaymentMethod());
        serviceExpense.setDescription(serviceExpenseDTO.getDescription());
        serviceExpense.setTotal(serviceExpenseDTO.getTotal());
        serviceExpense.setName(serviceExpenseDTO.getName());
        List<BankAccount> bankAccount =
                bankAccountRepository.findBankAccountByAccountNumber(serviceExpenseDTO.getBankAccountNumber());
        if (bankAccount.size() > 0) {
            serviceExpense.setBankAccount(bankAccount.get(0));
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số tài khoản không đúng");
        }

        Optional<Staff> staff = staffRepository.findById(serviceExpenseDTO.getStaffID());
        if (staff.isPresent()) {
            serviceExpense.setStaff((Staff) staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid staff ID");
        }
        return serviceExpense;
    }
}
