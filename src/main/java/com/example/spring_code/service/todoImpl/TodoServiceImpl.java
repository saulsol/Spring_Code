package com.example.spring_code.service.todoImpl;

import com.example.spring_code.domian.Todo;
import com.example.spring_code.dto.TodoDTO;
import com.example.spring_code.repository.TodoRepository;
import com.example.spring_code.service.todoInterface.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // SPRING @TRANSACTIONAL

import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {
        return null;
    }

    @Override
    public TodoDTO get(Long tno) {

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        return entityToDTO(todo);
    }


}
