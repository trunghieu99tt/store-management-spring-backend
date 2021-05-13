package com.projects.app.repository;

import com.projects.app.models.FinancialPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FinancialPlanRepository extends JpaRepository<FinancialPlan, Long>, JpaSpecificationExecutor<FinancialPlan> {

}
