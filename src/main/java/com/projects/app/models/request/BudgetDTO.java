package com.projects.app.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {

    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotBlank(message = "Please provide description for Budget")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    @NotBlank(message = "Please provide name for Budget")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

    @NotNull
    private long managerID;
}
