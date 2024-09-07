package com.example.spring_code.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> listDto;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalPage, totalCount, prevPage, nextPage, current;


    PageResponseDTO(List<E> listDto, PageRequestDTO pageRequestDTO, long total){
        this.listDto = listDto;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) total;

        // 끝 페이지 계산
        int endPageNum = (int) (10 * (Math.ceil(pageRequestDTO.getPage() / 10.0)));
        
        // 시작 페이지 계산
        int startPageNum = endPageNum - 9;

        // 최종 마지막 페이지 계산
        int lastPageNum = (int) Math.ceil(total / (double)pageRequestDTO.getSize());


        endPageNum = endPageNum > lastPageNum ? lastPageNum : endPageNum;

        this.prev = startPageNum > 1;
        this.next = total > (long) endPageNum * pageRequestDTO.getSize();
        this.pageNumList = IntStream.range(startPageNum, endPageNum).boxed().collect(Collectors.toList());
        this.prevPage = prev ? startPageNum - 1 : 0;
        this.nextPage = next ? endPageNum + 1 : 0;



    }



}
