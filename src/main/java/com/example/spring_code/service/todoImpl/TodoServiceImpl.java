package com.example.spring_code.service.todoImpl;

import com.example.spring_code.dto.TodoDTO;
import com.example.spring_code.service.todoInterface.TodoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // SPRING @TRANSACTIONAL

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    @Override
    public Long register(TodoDTO todoDTO) {
        return null;
    }
}
