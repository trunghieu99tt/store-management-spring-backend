package com.projects.app.models.expense;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Profit;
import com.projects.app.models.Report;
import com.projects.app.models.user.Staff;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @ManyToOne
    @JoinColumn(name = "bankAccountID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BankAccount bankAccount;

    @ManyToMany(mappedBy = "expenses", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @JsonIgnoreProperties
    private Collection<Report> reports;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Profit profit;

    @ManyToOne()
    @JoinColumn(name = "staffID")
    private Staff staff;
}

