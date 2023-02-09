package com.health.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class MainProgramDto {

    private Long programId;

    private String programNm;

    private String programDetail;

    private String imgUrl;

    private Integer price;

    @QueryProjection //쿼리dsl로 결과 조회시 MainItemDto 객체로 바로 받아올 수 있다.
    public MainProgramDto(Long programId, String programNm, String programDetail, String imgUrl, Integer price){
        this.programId = programId;
        this.programNm = programNm;
        this.programDetail = programDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}