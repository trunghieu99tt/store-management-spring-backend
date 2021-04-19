package riki.projects.todo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import riki.projects.todo.common.exception.model.BackendError;
import riki.projects.todo.common.response.ResponseTool;
import riki.projects.todo.common.response.model.APIResponse;
import riki.projects.todo.models.Todo;
import riki.projects.todo.services.TodoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
@Tag(name = "todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Operation(description = "View todo list", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Todo.class))),
                    responseCode = "200")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })

    @GetMapping("/")
    public List<Todo> getAllTodo(@RequestParam Integer limit) {
        return todoService.findAll(limit);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<APIResponse> createTodo(@Valid
                                                  @Parameter(description = "Todo model to create", required = true,
                                                          schema =
                                                  @Schema(implementation = Todo.class))
                                                  @RequestBody Todo todo) {
        Todo newTodo = todoService.add(todo);
        return ResponseTool.POST_OK(newTodo);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<APIResponse> getTodo(@PathVariable(name = "todoId") Long todoId) throws BackendError {
        Todo todo = todoService.getTodo(todoId);
        if (todo == null) {
            String message = "Invalid todoID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return ResponseTool.GET_OK(todo);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<APIResponse> editTodo(@PathVariable(name = "todoId") Long todoId, @RequestBody Todo todo
    ) {
        todo.setId(todoId);
        return ResponseTool.PUT_OK(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<APIResponse> deleteTodo(@PathVariable(name = "todoId") Long todoId) throws BackendError {
        boolean ok = todoService.deleteTodo(todoId);
        if (ok) {
            return ResponseTool.DELETE_OK();
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "There is no todo item with this id");
        }
    }

}
