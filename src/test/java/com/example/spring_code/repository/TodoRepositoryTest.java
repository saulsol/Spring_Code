package com.example.spring_code.repository;


import com.example.spring_code.domian.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testInsert(){
        for(int i=1; i<=100; i++){
            Todo todo = Todo.builder()
                    .title("title....")
                    .dueDate(LocalDate.of(2024,8,20))
                    .writer("userLIM")
                    .build();

            todoRepository.save(todo);
        }
    }
}