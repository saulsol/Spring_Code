package com.example.spring_code.repository;

import com.example.spring_code.domian.Todo;
import com.example.spring_code.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

}
