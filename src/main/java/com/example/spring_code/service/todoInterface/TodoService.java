package com.example.spring_code.service.todoInterface;

import com.example.spring_code.domian.Todo;
import com.example.spring_code.dto.TodoDTO;

public interface TodoService {
    Long register(TodoDTO todoDTO);

    void modify(TodoDTO todoDTO);

    void remove(Long tno);


    TodoDTO get(Long tno);

    default TodoDTO entityToDTO(Todo todo){

        return TodoDTO
                .builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .writer(todo.getWriter())
                .dueDate(todo.getDueDate())
                .build();
    }

    default Todo dtoToEntity(TodoDTO todoDTO){

        return Todo
                .builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .writer(todoDTO.getWriter())
                .dueDate(todoDTO.getDueDate())
                .build();
    }


}