package com.health.dto;

import org.modelmapper.ModelMapper;

import com.health.entity.ProgramImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramImgDto {
	private Long id;
	
	private String imgName; //이미지 파일명
	
	private String oriImgName; //원본 이미지 파일명
	
	private String imgUrl; //이미지 조회 경로
	
	private String repimgYn; //대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ProgramImgDto of(ProgramImg programImg) {
		return modelMapper.map(programImg, ProgramImgDto.class);
	}
}
