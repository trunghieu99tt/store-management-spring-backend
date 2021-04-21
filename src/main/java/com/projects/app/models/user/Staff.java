package com.projects.app.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff extends User {

    @NotBlank(message = "Please provide department for Staff")
    @Size(min = 1, max = 500)
    private String department;


    public Staff(long staffID) {
        this.setId(staffID);
    }
}
