package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.Budget;
import com.projects.app.models.Report;
import com.projects.app.models.request.BudgetDTO;
import com.projects.app.models.user.Manager;
import com.projects.app.models.user.User;
import com.projects.app.repository.BudgetRepository;
import com.projects.app.repository.ReportRepository;
import com.projects.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Budget> getAll() {
        return budgetRepository.findAll();
    }

    public Budget getOne(Long id) {
        Optional<Budget> budget = budgetRepository.findById(id);
        return budget.orElse(null);
    }

    public Budget createOne(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget updateOne(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Transactional
    public Boolean deleteOne(Long id) {
        Budget budget = getOne(id);
        if (budget != null) {
            Collection<Report> newReports = new ArrayList<>();
            budget.getReports().forEach(report -> {
                report.getBudgets().remove(budget);
                if (report.getRevenues().size() == 0 && report.getExpenses().size() == 0 && report.getBudgets().size() == 0) {
                    reportRepository.delete(report);
                } else {
                    newReports.add(report);
                }
            });
            reportRepository.saveAll(newReports);
            budgetRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Budget parseBudgetDTOToBudget(BudgetDTO budgetDTO) throws BackendError {
        Budget budget = new Budget();
        budget.setDescription(budgetDTO.getDescription());
        budget.setName(budgetDTO.getName());
        budget.setTotal(budgetDTO.getTotal());
        Optional<User> manage = userRepository.findById(budgetDTO.getManagerID());
        if (manage.isPresent()) {
            budget.setManager((Manager) manage.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Không tồn tại người quản lí này");
        }
        return budget;

    }
}
