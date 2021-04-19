package com.projects.app.common.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    private int status;
    private String message;
    private Object data;

}
