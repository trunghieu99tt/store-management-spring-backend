package com.projects.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "UserID", hidden = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Please provide name of user")
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank(message = "Please provide email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Please provide phone number")
    @Size(min = 9, max = 11)
    private String phoneNumber;

    @Column(unique = true)
    @NotBlank(message = "Please provide username")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "Please provide phone ")
    @Size(min = 1, max = 100)
    private String password;

    @NotBlank(message = "Please provide role")
    @Size(min = 1, max = 100)
    private String role;
}
