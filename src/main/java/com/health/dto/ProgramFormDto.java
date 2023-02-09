package com.health.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.health.constant.ProgramSellStatus;
import com.health.entity.Program;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramFormDto {
	
	private Long programId; //상품코드
	
	@NotBlank(message = "상품명은 필수 입력 값입니다.")
	private String programNm; //상품명
	
	@NotNull(message = "가격은 필수 입력 값입니다.")
	private int price; //가격
	
	@NotBlank(message = "상품 상세설명은 필수 입력 값입니다.")
	private String programDetail; //상품 상세설명
	
	private ProgramSellStatus programSellStatus; //상품 판매상태
	
	private List<ProgramImgDto> programImgDtoList = new ArrayList<>(); //상품 이미지 정보를 저장하는 리스트
	
	private List<Long> programImgIds = new ArrayList<>(); //상품의 이미지 아이디를 저장 -> 수정시에 이미지 아이디를 담아 둘 용도.
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Program createProgram() {
		return modelMapper.map(this, Program.class);
	}
	
	public static ProgramFormDto of(Program program) {
		return modelMapper.map(program, ProgramFormDto.class);
	}
}