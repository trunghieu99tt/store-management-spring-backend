package com.projects.app.config;

import com.projects.app.models.TodoValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
