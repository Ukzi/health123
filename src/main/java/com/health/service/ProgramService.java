package com.health.service;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.health.dto.MainProgramDto;
import com.health.dto.ProgramFormDto;
import com.health.dto.ProgramImgDto;
import com.health.dto.ProgramSearchDto;
import com.health.entity.Program;
import com.health.entity.ProgramImg;
import com.health.repository.ProgramImgRepository;
import com.health.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {
	private final ProgramRepository programRepository;
	private final ProgramImgService programImgService;
	private final ProgramImgRepository programImgRepository;
	
	//상품 등록
	public Long saveProgram(ProgramFormDto programFormDto, List<MultipartFile> programImgFileList) throws Exception {
		
		//상품등록
		Program program = programFormDto.createProgram();
		programRepository.save(program);
		
		//이미지 등록
		for(int i=0; i<programImgFileList.size(); i++) {
			ProgramImg programImg = new ProgramImg();
			programImg.setProgram(program);
			
			//첫번째 이미지 일때 대표 상품 이미지 여부 지정
			if(i == 0) { 
				programImg.setRepimgYn("Y");
			} else {
				programImg.setRepimgYn("N");
			}
			
			programImgService.saveProgramImg(programImg, programImgFileList.get(i));
		}
		
			return program.getId();
	}
	
	//상품 가져오기
	@Transactional(readOnly = true)
	public ProgramFormDto getProgramDtl(Long programId) {
		//1. item_img테이블의 이미지를 가져온다.
		List<ProgramImg> programImgList = programImgRepository.findByProgramIdOrderByIdAsc(programId);
		List<ProgramImgDto> programImgDtoList = new ArrayList<>();
		
		//엔티티 객체 -> dto객체로 변환
		for(ProgramImg programImg : programImgList) {
			ProgramImgDto programImgDto = ProgramImgDto.of(programImg);
			programImgDtoList.add(programImgDto);
		}
		
		//2. item테이블에 있는 데이터를 가져온다.
		Program program = programRepository.findById(programId)
				                  .orElseThrow(EntityNotFoundException::new);
		
		//엔티티 객체 -> dto객체로 변환
		ProgramFormDto programFormDto = ProgramFormDto.of(program);
		
		//상품의 이미지 정보를 넣어준다.
		programFormDto.setProgramImgDtoList(programImgDtoList);
		
		return programFormDto;
	}
	
	//상품 수정
	public Long updateProgram(ProgramFormDto programFormDto, List<MultipartFile> programImgFileList) throws Exception {
		
		Program program = programRepository.findById(programFormDto.getProgramId())
				.orElseThrow(EntityNotFoundException::new);
		
		program.updateProgram(programFormDto);
		
		List<Long> programImgIds = programFormDto.getProgramImgIds(); //상품 이미지 아이디 리스트를 조회
		
		for(int i=0; i<programImgFileList.size(); i++) {
			programImgService.updateProgramImg(programImgIds.get(i), programImgFileList.get(i));
		}
		
		return program.getId();
		
	}
	
	//상품 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<Program> getAdminProgramPage(ProgramSearchDto programSearchDto, Pageable pageable) {
		return programRepository.getAdminProgramPage(programSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainProgramDto> getMainProgramPage(ProgramSearchDto programSearchDto, Pageable pageable) {
		return programRepository.getMainProgramPage(programSearchDto, pageable);
	}
	
	

}