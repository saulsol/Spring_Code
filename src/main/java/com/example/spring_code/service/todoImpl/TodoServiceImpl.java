package com.example.spring_code.service.todoImpl;

import com.example.spring_code.domian.Todo;
import com.example.spring_code.dto.PageRequestDTO;
import com.example.spring_code.dto.PageResponseDTO;
import com.example.spring_code.dto.TodoDTO;
import com.example.spring_code.repository.TodoRepository;
import com.example.spring_code.service.todoInterface.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // SPRING @TRANSACTIONAL

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {
        Todo todo = dtoToEntity(todoDTO);
        return todoRepository.save(todo).getTno();
    }

    @Override
    public void modify(TodoDTO todoDTO) {
        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
        Todo todo = result.orElseThrow();

        todo.changeComplete(todoDTO.isComplete());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeContent(todoDTO.getContent());
        todo.changeTitle(todoDTO.getTitle());

        todoRepository.save(todo);
    }

    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {

        Page<Todo> result = todoRepository.search(pageRequestDTO);
        List<TodoDTO> todoDTOList  = result
                .get()
                .map(todo -> entityToDTO(todo))
                .toList();

        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(todoDTOList)
                .pageRequestDTO(pageRequestDTO)
                .total(result.getTotalElements())
                .build();

        return responseDTO;
    }

    @Override
    public TodoDTO get(Long tno) {

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        return entityToDTO(todo);
    }


}
