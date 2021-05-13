package com.projects.app.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryExpenseDTO extends ExpenseDTO {

    @NotNull
    private Long employeeID;

    @NotNull
    private String name;

}
