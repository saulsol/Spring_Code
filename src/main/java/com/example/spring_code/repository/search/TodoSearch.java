package com.example.spring_code.repository.search;

import com.example.spring_code.domian.Todo;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search();
}
