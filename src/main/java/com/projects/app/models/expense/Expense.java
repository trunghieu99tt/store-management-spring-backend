package com.projects.app.models.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Profit;
import com.projects.app.models.Report;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "expenseID", hidden = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private Date date;

    @NotBlank(message = "Please provide description for expense")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;


    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotBlank(message = "Please provide payment method for expense")
    @Size(min = 1, max = 500, message = "Payment method must not be longer than 500 characters")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "bankAccountID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "reportID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Report report;

    @ManyToOne
    @JoinColumn(name = "profitID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Profit profit;
}

