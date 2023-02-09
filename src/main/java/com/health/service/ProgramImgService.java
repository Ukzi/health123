package com.health.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.health.entity.ProgramImg;
import com.health.repository.ProgramImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramImgService {
	
	@Value("${programImgLocation}")
	private String programImgLocation; 
	
	private final ProgramImgRepository programImgRepository;
	
	private final FileService fileService;
	
	//이미지 저장 메소드
	public void saveProgramImg(ProgramImg programImg, MultipartFile programImgFile) throws Exception {
		String oriImgName = programImgFile.getOriginalFilename(); //파일 이름
		String imgName = "";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(programImgLocation, oriImgName, programImgFile.getBytes());
			imgUrl = "/images/program/" + imgName;
		}
		
		//상품 이미지 정보 저장
		programImg.updateProgramImg(oriImgName, imgName, imgUrl);
		programImgRepository.save(programImg);
		
	}
	
	//이미지 업데이트 메소드
	public void updateProgramImg(Long programImgId, MultipartFile programImgFile) throws Exception {
		if(!programImgFile.isEmpty()) { //파일이 있으면
			ProgramImg savedProgramImg = programImgRepository.findById(programImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedProgramImg.getImgName())) {
				fileService.deleteFile(programImgLocation + "/" + savedProgramImg.getImgName());
			}
			
			//수정된 이미지 파일 업로드
			String oriImgName = programImgFile.getOriginalFilename(); 
			String imgName = fileService.uploadFile(programImgLocation, oriImgName, programImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			//★ savedItemImg는 현재 영속상태이므로 데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜잭션이 끝날때 update쿼리가 실행된다.
			//-> 엔티티가 반드시 영속상태여야 한다.
			savedProgramImg.updateProgramImg(oriImgName, imgName, imgUrl);
		}
	}
}