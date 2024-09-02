package com.example.spring_code.service.todoImpl;

import com.example.spring_code.domian.Todo;
import com.example.spring_code.dto.TodoDTO;
import com.example.spring_code.repository.TodoRepository;
import com.example.spring_code.service.todoInterface.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.time.LocalDate;
import java.util.Optional;


@SpringBootTest
@Log4j2
@Rollback(false)
class TodoServiceImplTest {


    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @Test
    public void testGet(){
        Long tno = 50L;
        log.info(todoService.get(tno));
    }

    @Test
    public void testRegister(){
        TodoDTO todoDTO = TodoDTO.builder()
                .title("ZZZ")
                .content("...content")
                .dueDate(LocalDate.now())
                .build();
        log.info(todoService.register(todoDTO));
    }

    @Test
    public void testModify(){}



}