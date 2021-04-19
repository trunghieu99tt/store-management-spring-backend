package com.projects.app.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDTO {
    private HttpStatus status;
    private String message;
    private String stackStrace;
}
