package com.projects.app.models.expense;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalaryExpense extends Expense {
    private Long employeeID;

    @NotBlank(message = "Please provide name for Revenue")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

}
