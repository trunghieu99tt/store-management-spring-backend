package riki.projects.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import riki.projects.todo.models.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
