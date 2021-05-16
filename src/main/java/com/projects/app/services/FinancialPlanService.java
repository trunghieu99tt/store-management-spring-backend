package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.Budget;
import com.projects.app.models.FinancialPlan;
import com.projects.app.models.request.BudgetDTO;
import com.projects.app.models.request.FinancialPlanDTO;
import com.projects.app.models.user.Manager;
import com.projects.app.models.user.User;
import com.projects.app.repository.BudgetRepository;
import com.projects.app.repository.FinancialPlanRepository;
import com.projects.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialPlanService {
    @Autowired
    private FinancialPlanRepository financialPlanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    public List<FinancialPlan> getAll() {
        return financialPlanRepository.findAll();
    }

    public FinancialPlan getOne(Long id) {
        Optional<FinancialPlan> financialPlan = financialPlanRepository.findById(id);
        return financialPlan.orElse(null);
    }

    public FinancialPlan createOne(FinancialPlan financialPlan) {
        return financialPlanRepository.save(financialPlan);
    }

    public FinancialPlan updateOne(FinancialPlan financialPlan) {
        return financialPlanRepository.save(financialPlan);
    }

    public Boolean deleteOne(Long id) {
        FinancialPlan financialPlan = getOne(id);
        if (financialPlan != null) {
            financialPlanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public FinancialPlan parseFinancialPlanDTOToFinancialPlan(FinancialPlanDTO financialPlanDTO) throws BackendError {
        FinancialPlan financialPlan = new FinancialPlan();
        financialPlan.setDescription(financialPlanDTO.getDescription());
        financialPlan.setTotal(financialPlanDTO.getTotal());
        Optional<User> manage = userRepository.findById(financialPlanDTO.getManageID());
        Optional<Budget> budget = budgetRepository.findById(financialPlanDTO.getBudgetID());
        if(manage.isPresent() && budget.isPresent()){
            financialPlan.setManager((Manager) manage.get());
            financialPlan.setBudget((Budget) budget.get());
        }else{
            throw new BackendError(HttpStatus.BAD_REQUEST,"Thao tac khong ton tai");
        }
        return financialPlan;

    }
}
