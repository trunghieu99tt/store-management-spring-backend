package com.projects.app.models.request;


import com.projects.app.models.Budget;
import com.projects.app.models.Revenue;
import com.projects.app.models.expense.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    @NotNull
    private Long staffID;

    @NotNull
    private Date dateFrom;

    @NotNull
    private Date dateTo;

    @NotNull
    private String description;

    @NotNull
    private Collection<Revenue> revenues;

    @NotNull
    private Collection<Expense> expenses;


    @NotNull
    private Collection<Budget> budgets;

    @NotNull
    private float budget;

    @NotNull
    private float expense;

    @NotNull
    private float revenue;

    @NotNull
    private float profit;


}
