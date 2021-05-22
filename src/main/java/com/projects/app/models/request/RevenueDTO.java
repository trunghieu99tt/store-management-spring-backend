package com.projects.app.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTO {

    @NotBlank(message = "Please provide name for Revenue")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

    @NotNull
    @Positive(message = "quantity must be positive")
    private int quantity;

    @NotNull
    @Positive(message = "price unit must be positive")
    private float priceUnit;

    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotBlank(message = "Please provide description for expense")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    private Date createdAt;

    @NotNull
    private String bankAccountNumber;

    @NotNull
    private long staffID;
}
