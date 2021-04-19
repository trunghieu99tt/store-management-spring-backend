package riki.projects.todo.common.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackendError extends Exception {
    private HttpStatus statusCode;
    private String message;
}
