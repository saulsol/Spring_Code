package com.example.spring_code.service.todoImpl;

import com.example.spring_code.service.todoInterface.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Rollback(false)
class TodoServiceImplTest {

    @Autowired
    TodoService todoService;

    @Test
    public void testGet(){
        Long tno = 50L;
        log.info(todoService.get(tno));
    }
}