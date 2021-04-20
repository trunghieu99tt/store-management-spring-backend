package com.projects.app.models.expense;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shopping extends Expense {

    @NotNull
    @Positive(message = "Product ID must be positive")
    private Long productID;

    @NotBlank(message = "Please provide name for Shopping")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

    @NotNull
    @Positive(message = "quantity must be positive")
    private int quantity;

    @NotNull
    @Positive(message = "priceUnit must be positive")
    private float priceUnit;
}
