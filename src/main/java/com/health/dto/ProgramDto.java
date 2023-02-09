package com.health.dto;

import java.time.LocalDateTime;

import com.health.constant.ProgramSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramDto {
	private Long programId; //상품코드

	private String programNm; //상품명
		
	private int price; //가격

	private String programDetail; //상품 상세설명
		
	private ProgramSellStatus programSellStatus; //상품 판매상태
		
	private LocalDateTime regTime; //등록 시간

	private LocalDateTime updateTime; //수정 시간
}