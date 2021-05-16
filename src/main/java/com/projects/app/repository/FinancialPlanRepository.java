package com.projects.app.repository;

import com.projects.app.models.FinancialPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPlanRepository extends JpaRepository<FinancialPlan,Long> {
}
