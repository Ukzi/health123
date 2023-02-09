package com.health.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.health.constant.ProgramSellStatus;
import com.health.dto.ProgramFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="program") //테이블명
@Getter
@Setter
@ToString
public class Program extends BaseEntity {
	//not null이 아닐때는 필드 타입을 객체(예 int - Integer)로 지정해야 한다. 
	
	@Id
	@Column(name="program_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programId; //상품코드
	
	@Column(nullable = false, length = 50)
	private String programNm; //상품명
	
	@Column(nullable = false, name = "price")
	private int price; //가격
	
	@Lob
	@Column(nullable = false)
	private String programDetail; //상품 상세설명
	
	@Enumerated(EnumType.STRING)
	private ProgramSellStatus programSellStatus; //상품 판매상태
	
	public void updateProgram(ProgramFormDto programFormDto) {
		this.programNm = programFormDto.getProgramNm();
		this.price = programFormDto.getPrice();
		this.programDetail = programFormDto.getProgramDetail();
		this.programSellStatus = programFormDto.getProgramSellStatus();
	}
	
}