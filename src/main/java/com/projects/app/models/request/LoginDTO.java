package com.projects.app.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Column(unique = true)
    @Size(max = 20)
    private String username;

    @Size(min = 8, max = 20)
    private String password;

}
