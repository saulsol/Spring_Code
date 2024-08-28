package com.example.spring_code.repository;


import com.example.spring_code.domian.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;


    // INSERT
    // SQL : INSERT
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

    // GET
    // SQL : SELECT
    @Test
    public void testRead() {
        Long tno = 33L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
    }


    // MODIFY
    // SQL : SELECT => UPDATE
    @Test
    public void testModify(){
        Long tno = 33L;

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        todo.changeTitle("change title");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.now());

        todoRepository.save(todo);
    }

    // DELETE
    // SQL : SELECT => DELETE
    @Test
    public void deleteTest(){
        Long tno = 33L;
        todoRepository.deleteById(tno);

    }


}