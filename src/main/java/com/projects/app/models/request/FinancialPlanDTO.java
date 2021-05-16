package com.projects.app.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.Budget;
import com.projects.app.models.user.Manager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialPlanDTO {
    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotBlank(message = "Please provide description for FinancialPlan")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    @NotNull
    private Long budgetID;

    @NotNull
    private Long managerID;

    @NotBlank(message = "Please provide name for FinancialPlan")
    @Size(min = 1, max = 50, message = "name must not be longer than 50 characters")
    private String name;
}
