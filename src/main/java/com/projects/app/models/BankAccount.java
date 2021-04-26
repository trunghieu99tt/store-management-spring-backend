package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.expense.Expense;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "bankAccountID", hidden = true)
    @Column(name = "bankAccountID")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 11)
    private String accountNumber;

    @NotBlank
    @Size(min = 1, max = 100)
    private String bankName;

    @NotBlank
    @Size(min = 1, max = 100)
    private String branch;

    public BankAccount(long bankAccountID) {
        this.id = bankAccountID;
    }

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @JsonIgnoreProperties
    private Collection<Revenue> revenues;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties
    @JsonIgnore
    private Collection<Expense> expenses;


}
