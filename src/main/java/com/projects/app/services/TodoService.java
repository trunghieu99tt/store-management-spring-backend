package com.projects.app.services;

import com.projects.app.models.Todo;
import com.projects.app.models.TodoValidator;
import com.projects.app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoValidator todoValidator;

    /**
     * Get todo list
     *
     * @param limit
     * @return List<Todo>
     */
    public List<Todo> findAll(Integer limit) {
        return Optional.ofNullable(limit)
                .map(value -> todoRepository.findAll(PageRequest.of(0, value)).getContent())
                .orElseGet(() -> todoRepository.findAll());
    }

    /**
     * Add new todo
     *
     * @param todo - new todo item
     * @return Todo
     */
    public Todo add(Todo todo) {
        if (todoValidator.isValid(todo)) {
            return todoRepository.save(todo);
        }
        return null;
    }

    /**
     * Get todo by ID
     *
     * @param todoId - id of todo item to be got
     * @return Todo
     */
    public Todo getTodo(Long todoId) {
        Optional<Todo> todo = todoRepository.findById(todoId);
        return todo.orElse(null);
    }


    /**
     * Update todo
     *
     * @param updatedTodo - new todo item
     * @return Todo
     */
    public Todo updateTodo(Todo updatedTodo) {
        return todoRepository.save(updatedTodo);
    }

    /**
     * @param todoId - id of todo item need to be deleted
     * @return boolean - true if delete successfully, false otherwise
     */
    public boolean deleteTodo(Long todoId) {
        if (getTodo(todoId) != null) {
            todoRepository.deleteById(todoId);
            return true;
        }
        return false;
    }


}
