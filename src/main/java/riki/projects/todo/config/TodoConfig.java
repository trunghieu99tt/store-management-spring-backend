package riki.projects.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import riki.projects.todo.models.TodoValidator;

@Configuration
public class TodoConfig {

    /**
     * Create TodoValidator Bean
     */
    @Bean
    public TodoValidator validator() {
        return new TodoValidator();
    }


}
