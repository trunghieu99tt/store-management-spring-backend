package com.projects.app.models.expense;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Schema(description = "expenseID", hidden = true)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccountID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BankAccount bankAccount;

//    @ManyToMany(mappedBy = "expenses", cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @JsonIgnore
//    private Collection<Report> reports;

    @ManyToMany(mappedBy = "expenses")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties
    private Collection<Report> reports;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Profit profit;
}

