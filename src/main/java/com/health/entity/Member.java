package com.health.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.health.constant.ExerciseAmount;
import com.health.constant.ExercisePurpose;
import com.health.constant.Role;

/*import org.springframework.security.crypto.password.PasswordEncoder;*/

/*import com.health.constant.Role;*/
import com.health.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member") //테이블명
@Getter
@Setter
@ToString
public class Member extends BaseEntity {
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String age;
	
	private String tall;
	
	private String weight;
	
	@Enumerated(EnumType.STRING)
	private ExerciseAmount exerciseAmount;
	
	@Enumerated(EnumType.STRING)
	private ExercisePurpose exercisePurpose;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setAge(memberFormDto.getAge());
		member.setTall(memberFormDto.getTall());
		member.setWeight(memberFormDto.getWeight());
		member.setExerciseAmount(memberFormDto.getExerciseAmount());
		member.setExercisePurpose(memberFormDto.getExercisePurpose());
		member.setEmail(memberFormDto.getEmail());

		
		String password = passwordEncoder.encode(memberFormDto.getPassword()); //비밀번호 암호화
		member.setPassword(password);
		
		member.setRole(Role.USER);
		
		return member;
	}
}
