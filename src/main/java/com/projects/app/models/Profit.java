package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "profitID", hidden = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String description;

    @NotNull
    @Positive(message = "Total must be positive")
    private float total;

    @ManyToOne
    @JoinColumn(name = "reportID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Report report;
}
