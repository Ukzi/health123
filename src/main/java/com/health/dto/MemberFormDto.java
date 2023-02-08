package com.health.dto;

import java.util.List;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import com.health.constant.ExerciseAmount;
import com.health.constant.ExercisePurpose;

import lombok.Getter;
import lombok.Setter;

//회원가입으로부터 넘어오는 가입정보를 담을DTO

@Getter
@Setter
public class MemberFormDto {
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "나이는 필수 입력 값입니다.")
	private String age;
	
	@NotBlank(message = "키는 필수 입력 값입니다.")
	private String tall;
	
	@NotBlank(message = "몸무게는 필수 입력 값입니다.")
	private String weight;
	
	private ExerciseAmount exerciseAmount;
	
	private ExercisePurpose exercisePurpose;
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String password;
	
}
