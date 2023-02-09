package com.health.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.health.dto.MainProgramDto;
import com.health.dto.ProgramSearchDto;
import com.health.entity.Program;

//1. 사용자 정의 인터페이스 작성
public interface ProgramRepositoryCustom {
	//상품관리 페이지 아이템을 가져온다.
	Page<Program> getAdminProgramPage(ProgramSearchDto programSearchDto, Pageable pageable);
	
	//메인화면에 뿌리는 아이템을 가져온다.
	Page<MainProgramDto> getMainProgramPage(ProgramSearchDto programSearchDto, Pageable pageable);
}
