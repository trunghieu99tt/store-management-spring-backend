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
public class ShoppingExpenseDTO extends ExpenseDTO {
    @NotNull
    private Long productID;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    @NotNull
    private float priceUnit;

}
