package com.projects.app.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.BankAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

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
    @NotNull
    private String bankAccountNumber;
}
