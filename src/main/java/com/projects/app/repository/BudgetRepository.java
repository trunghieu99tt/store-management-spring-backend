package com.projects.app.repository;

import com.projects.app.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findBudgetByMonthAndYear(int month, int year);
}
