package com.projects.app.services.expense;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Report;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ExpenseDTO;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.ReportRepository;
import com.projects.app.repository.expense.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class ExpenseService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    ReportRepository reportRepository;

    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    public Expense getOne(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.orElse(null);
    }

    public Expense createOne(Expense ex) {
        return expenseRepository.save(ex);
    }

    @Transactional
    public Boolean deleteOne(Long id) {
        Expense expense = getOne(id);
        if (expense != null) {
            Collection<Report> newReports = new ArrayList<>();
            expense.getReports().forEach(r -> {
                r.getExpenses().remove(expense);
                if (r.getExpenses().size() == 0 && r.getRevenues().size() == 0) {
                    reportRepository.delete(r);
                } else {
                    newReports.add(r);
                }
            });
            reportRepository.saveAll(newReports);
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Expense updateOne(Expense ex) {
        return expenseRepository.save(ex);
    }

    public Expense parseExpenseDTOToExpense(ExpenseDTO expenseDTO) throws BackendError {
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());
        expense.setDescription(expenseDTO.getDescription());
        expense.setTotal(expenseDTO.getTotal());
        List<BankAccount> bankAccount =
                bankAccountRepository.findBankAccountByAccountNumber(expenseDTO.getBankAccountNumber());
        if (bankAccount.size() > 0) {
            expense.setBankAccount(bankAccount.get(0));
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số tài khoản không đúng");
        }
        return expense;
    }

    public List<Expense> getStatistic(Date dayStart, Date dayEnd) {
        return expenseRepository.findByDateBetween(dayStart, dayEnd);
    }
}
