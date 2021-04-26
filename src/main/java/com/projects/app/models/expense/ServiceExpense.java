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
@AllArgsConstructor
@NoArgsConstructor
public class ServiceExpense extends Expense {
    @NotBlank(message = "Please provide name for expense")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

}
