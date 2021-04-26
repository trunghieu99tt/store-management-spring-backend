package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.app.models.user.Staff;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "revenue")
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    @NotBlank(message = "Please provide name for Revenue")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

    @NotNull
    @Positive(message = "quantity must be positive")
    private int quantity;

    @NotNull
    @Positive(message = "price unit must be positive")
    private float priceUnit;

    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @NotBlank(message = "Please provide description for expense")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "bankAccountID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BankAccount bankAccount;

    @ManyToOne()
    @JoinColumn(name = "staffID")
    private Staff staff;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Profit profit;

    @ManyToMany(mappedBy = "revenues", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties
    @JsonIgnore
    private Collection<Report> reports;

}