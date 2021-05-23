package com.projects.app.repository.expense;

import com.projects.app.models.expense.EmployeeSalaryExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSalaryExpenseRepository extends JpaRepository<EmployeeSalaryExpense, Long> {
}
