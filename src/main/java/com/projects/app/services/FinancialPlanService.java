package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Budget;
import com.projects.app.models.FinancialPlan;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ExpenseDTO;
import com.projects.app.models.request.FinancialPlanDTO;
import com.projects.app.models.user.Manager;
import com.projects.app.repository.BudgetRepository;
import com.projects.app.repository.FinancePlanRepository;
import com.projects.app.repository.ManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FinancialPlanService {
    @Autowired
    private FinancePlanRepository financePlanRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ManageRepository manageRepository;

    public Page<FinancialPlan> getAllFinancialPlan(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return financePlanRepository.findAll(pageable);
    }

    public boolean deleteFinancialPlan(Long id){
        if(financePlanRepository.existsById(id)){
            financePlanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public FinancialPlan addFinancialPlan(FinancialPlan newFinancialPlan){
        FinancialPlan result = financePlanRepository.save(newFinancialPlan);
        result.getBudget();
        return result;
    }

    public FinancialPlan updateFinancePlan(FinancialPlan updatedFinancePlan) throws BackendError{
        if(!financePlanRepository.existsById(updatedFinancePlan.getId()))
            throw new BackendError(HttpStatus.BAD_REQUEST, "financialPlan không tồn tại");
        return financePlanRepository.saveAndFlush(updatedFinancePlan);
    }

    public FinancialPlan parseFinancialPlanDTOToFinancialPlan(FinancialPlanDTO financialPlanDTO) throws BackendError {
        FinancialPlan financialPlan = new FinancialPlan();
        Budget budget = new Budget();
        Manager manager = new Manager();
        financialPlan.setName(financialPlanDTO.getName());
        financialPlan.setDescription(financialPlanDTO.getDescription());
        financialPlan.setTotal(financialPlanDTO.getTotal());
        budget.setId(financialPlanDTO.getBudgetID());
        financialPlan.setBudget(budget);
        manager.setId(financialPlanDTO.getManagerID());
        financialPlan.setManager(manager);
        financialPlan.setCreateDate(new Date());
        if(!manageRepository.existsById(financialPlanDTO.getManagerID()))
            throw new BackendError(HttpStatus.BAD_REQUEST,"Không tồn tại người quản lý");
        if(!budgetRepository.existsById(financialPlanDTO.getBudgetID()))
            throw new BackendError(HttpStatus.BAD_REQUEST,"Không tồn tại budget");

        return financialPlan;
    }
}
