package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.models.user.Staff;
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
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "reportID", hidden = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
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

    @ManyToOne
    @JoinColumn(name = "staffID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Staff staff;

}
