package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.app.models.user.Manager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Budget {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "budgetID", hidden = true)
    private Long id;

    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @NotBlank(message = "Please provide description for Budget")
    @Size(min = 1, max = 500, message = "Description must not be longer than 500 characters")
    private String description;

    @NotBlank(message = "Please provide name for Budget")
    @Size(min = 1, max = 500, message = "Name must not be longer than 500 characters")
    private String name;

    @NotBlank(message = "Please provide month for Budget")
    @Positive(message = "Month must be positive")
    private int month;

    @NotBlank(message = "Please provide year for Budget")
    @Positive(message = "Month must be positive")
    private int year;


    @ManyToOne()
    @JoinColumn(name = "manageID")
    private Manager manager;

    @ManyToMany(mappedBy = "budgets", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties
    @JsonIgnore
    private Collection<Report> reports;

}
