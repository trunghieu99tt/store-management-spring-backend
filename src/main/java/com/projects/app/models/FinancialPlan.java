package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.user.Manager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "financialPlanID", hidden = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotBlank(message = "Please provide description for FinancialPlan")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "budgetID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "managerID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Manager manager;

}
