package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.entity.ProgramImg;

public interface ProgramImgRepository extends JpaRepository<ProgramImg, Long>  {
	List<ProgramImg> findByProgramIdOrderByIdAsc(Long programId);
	
	//상품의 대표 이미지를 찾음
	ProgramImg findByProgramIdAndRepimgYn(Long programId, String repimgYn);
	
}

