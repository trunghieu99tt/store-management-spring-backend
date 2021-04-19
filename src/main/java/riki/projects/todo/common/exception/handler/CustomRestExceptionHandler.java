package riki.projects.todo.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import riki.projects.todo.common.exception.dto.ApiErrorDTO;
import riki.projects.todo.common.exception.model.BackendError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomRestExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        // Get error value
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(errors.toString());
        apiErrorDTO.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
    }

    @ExceptionHandler(value = BackendError.class)
    public ResponseEntity<ApiErrorDTO> handleCustomBackendError(BackendError ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        apiErrorDTO.setStatus(ex.getStatusCode());
        return ResponseEntity.status(ex.getStatusCode()).body(apiErrorDTO);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorDTO> handleAll(Exception ex) {
        System.out.println(ex);
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        apiErrorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorDTO);
    }
}

