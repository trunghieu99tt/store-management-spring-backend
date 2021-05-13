package com.projects.app.services;

import com.projects.app.models.FinancialPlan;
import com.projects.app.repository.FinancialPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinancialPlanService {
    @Autowired
    private FinancialPlanRepository financialPlanRepository;

    /**
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<FinancialPlan> getAllFinancialPlan(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        System.out.println(financialPlanRepository.findAll(pageable));
        return financialPlanRepository.findAll(pageable);
    }

    /**
     * @param id
     * @return FinancialPlan or null
     */
    public FinancialPlan getFinancialPlan(Long id) {
        Optional<FinancialPlan> financialPlan = financialPlanRepository.findById(id);
        return financialPlan.orElse(null);
    }

    /**
     * @param financialPlan
     * @return FinancialPlan or null
     */
    public FinancialPlan addOrUpdateFinancialPlan(FinancialPlan financialPlan) {
        return financialPlanRepository.save(financialPlan);
    }

    /**
     *
     * @param id
     * @return boolean
     */
    public boolean deleteFinancialPlan(Long id) {
        if (getFinancialPlan(id) != null) {
            financialPlanRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
