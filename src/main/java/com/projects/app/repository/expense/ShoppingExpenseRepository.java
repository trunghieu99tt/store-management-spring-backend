package com.projects.app.repository.expense;

import com.projects.app.models.expense.ShoppingExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingExpenseRepository extends JpaRepository<ShoppingExpense, Long> {
}
