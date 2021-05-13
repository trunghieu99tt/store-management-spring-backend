package com.projects.app.repository.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSalaryExpenseRepository extends JpaRepository<com.projects.app.models.expense.EmployeeSalaryExpense,
        Long> {
}
