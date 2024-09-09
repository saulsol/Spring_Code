package com.example.spring_code.controller;

import com.example.spring_code.dto.PageRequestDTO;
import com.example.spring_code.dto.TodoDTO;
import com.example.spring_code.service.todoInterface.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{tno}")
    public ResponseEntity<?> get(@PathVariable("tno") Long tno){
        return ResponseEntity.ok().body(todoService.get(tno));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(PageRequestDTO pageRequestDTO){
        return ResponseEntity.ok().body(todoService.getList(pageRequestDTO));
    }

}
