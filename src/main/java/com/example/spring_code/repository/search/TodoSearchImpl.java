package com.example.spring_code.repository.search;

import com.example.spring_code.domian.QTodo;
import com.example.spring_code.domian.Todo;
import com.example.spring_code.dto.PageRequestDTO;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {
    public TodoSearchImpl() {
        super(Todo.class); // 도메인 클래스 지정
    }

    @Override
    public Page<Todo> search(PageRequestDTO pageRequestDTO) {

        QTodo qTodo = QTodo.todo;
        JPQLQuery<Todo> query = from(qTodo);
        // 검색문 필요할 땐 where 절 추가해야한다.


        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
