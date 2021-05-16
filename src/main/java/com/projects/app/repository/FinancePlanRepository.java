package com.projects.app.repository;

import com.projects.app.models.FinancialPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface FinancePlanRepository extends JpaRepository<FinancialPlan, Long> {
    Page<FinancialPlan> findAll(Pageable pageable);
}
