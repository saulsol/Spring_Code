package com.example.spring_code.repository;


import com.example.spring_code.domian.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;


    // INSERT
    // SQL : INSERT

    // 이유 : 엔티티에 식별자가 존재하지 않는다면 save() 메소드는 바로 INSERT 쿼리를 실행한다.
        @Test
        public void testInsert(){

        for(int i=1; i<=100; i++){
            Todo todo = Todo.builder()
//                    .tno((long) i) // 식별자가 있는 경우 SELECT 문 나감(기본키 생성 전략이 없는 경우)
                    .title("title....")
                    .dueDate(LocalDate.of(2024,8,20))
                    .writer("userLIM")
                    .build();

            todoRepository.save(todo);
        }
    }

    // GET
    // SQL : findById(SELECT)
    @Test
    public void testRead() {
        Long tno = 33L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
    }


    // MODIFY
    // SQL : findByID(SELECT) => save(SELECT => UPDATE)
    // 식별자가 존재하는 경우 save() 메소드는 SELECT 문을 한 번 더 실행한다. 이는 데이터베이스와의 동기화를 목적으로 진행한다.
    @Test
    public void testModify(){
        Long tno = 33L;

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        todo.changeTitle("change title");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.now());

        todoRepository.save(todo);
    }

    // DELETE
    // SQL : SELECT => DELETE
    @Test
    public void deleteTest(){
        Long tno = 33L;
        todoRepository.deleteById(tno);
    }


    @Test
    public void testPaging(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending()); // 내림차순 정렬, 페이지 번호 0, 사이즈 10 까지 꺼내기
        Page<Todo> result = todoRepository.findAll(pageable); // SELECT FROM ORDERBY LIMIT, SELECT count(tno) FROM => getTotalElements() 에 대한 선 준비
        System.out.println(result);
        log.info(result.stream().count()); // 10
        log.info(result.getTotalElements()); // 100
        log.info(result.getTotalPages()); // 10
        result.getContent().forEach(log::info);

    }

    @Test
    public void testSearch(){
            todoRepository.search();
    }


}