package com.health.dto;

import com.health.constant.ProgramSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramSearchDto {
	private String searchDateType;
	private ProgramSellStatus searchSellStatus;
	private String searchBy;
	private String searchQuery = "";
}
