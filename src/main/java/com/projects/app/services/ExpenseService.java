package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Revenue;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ExpenseDTO;
import com.projects.app.models.request.RevenueDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.models.user.User;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService  {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    public List<Expense> getAll(){
        return expenseRepository.findAll();
    }
    public  Expense getOne(Long id){
        Optional<Expense>  expense = expenseRepository.findById(id);
        return expense.orElse(null);
    }
    public Expense createOne(Expense ex){
        return expenseRepository.save(ex);
    }
    public Boolean deleteOne(Long id){
        if (getOne(id)!=null){
            expenseRepository.deleteById(id);
            return true;

        }
        return false;
    }
    public Expense updateOne(Expense ex){
        return expenseRepository.save(ex);
    }
    public Expense parseExpenseDTOToExpense(ExpenseDTO expenseDTO) throws BackendError {
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());
        expense.setDescription(expenseDTO.getDescription());
        expense.setTotal(expenseDTO.getTotal());
        System.out.println(expenseDTO.getBankAccountNumber());
        List<BankAccount> bankAccount =
                bankAccountRepository.findBankAccountByAccountNumber(expenseDTO.getBankAccountNumber());
        if (bankAccount.size() > 0) {
            expense.setBankAccount(bankAccount.get(0));
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số tài khoản không đúng");
        }

        return expense;
    }

}
