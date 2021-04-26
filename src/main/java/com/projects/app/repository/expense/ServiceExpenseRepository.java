package com.projects.app.repository.expense;

import com.projects.app.models.expense.ServiceExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceExpenseRepository extends JpaRepository<ServiceExpense, Long> {
}
