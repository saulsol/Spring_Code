package com.example.spring_code.repository.search;

import com.example.spring_code.domian.QTodo;
import com.example.spring_code.domian.Todo;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {
    public TodoSearchImpl() {
        super(Todo.class); // 도메인 클래스 지정
    }

    @Override
    public Page<Todo> search() {

        QTodo qTodo = QTodo.todo;
        JPQLQuery<Todo> query = from(qTodo);
        query.where(qTodo.title.contains("1")).fetch();

        Pageable pageable = PageRequest.of(1, 10, Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        query.fetch(); // 목록 데이터

        query.fetchCount();

        return null;
    }
}
