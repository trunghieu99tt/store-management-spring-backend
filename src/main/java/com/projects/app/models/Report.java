package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.user.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    @Column(unique = true)
    private Long id;

    @NotBlank(message = "Please provide description for Report")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    @NotNull
    private Date reportDate;

    @NotNull
    private Date reportFrom;

    @NotNull
    private Date reportTo;

    @NotNull
    @Positive(message = "expense must be positive")
    private float expense;


    @NotNull
    @Positive(message = "revenue must be positive")
    private float revenue;

    @NotNull
    @Positive(message = "profit must be positive")
    private float profit;

    @ManyToOne()
    @JoinColumn(name = "staffID")
    private Staff staff;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "report_revenue",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "revenue_id"))
    private Collection<Revenue> revenues;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "report_expense",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id"))
    private Collection<Expense> expenses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "report_budget",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "budget_id"))
    private Collection<Budget> budgets;

}
