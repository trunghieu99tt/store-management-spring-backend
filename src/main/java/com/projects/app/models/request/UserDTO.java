package com.projects.app.models.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

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
    @JsonIgnore
    @Size(min = 1, max = 100)
    private String password;

    @NotBlank(message = "Please provide role")
    @Size(min = 1, max = 100)
    private String role;
}
