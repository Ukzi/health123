package com.health.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.health.dto.OrderDto;
import com.health.dto.OrderHistDto;
import com.health.dto.OrderProgramDto;
import com.health.entity.Member;
import com.health.entity.Order;
import com.health.entity.OrderProgram;
import com.health.entity.Program;
import com.health.entity.ProgramImg;
import com.health.repository.MemberRepository;
import com.health.repository.OrderRepository;
import com.health.repository.ProgramImgRepository;
import com.health.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;
@Service //service 클래스의 역할
@Transactional //서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려 준다. 
@RequiredArgsConstructor
public class OrderService {
	private final ProgramRepository programRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepositorty;
	private final ProgramImgRepository programImgRepositorty;
	
	public Long order(OrderDto orderDto, String email) {
		Program program = programRepository.findById(orderDto.getProgramId())
                .orElseThrow(EntityNotFoundException::new);
		
		Member member = memberRepository.findByEmail(email);
		
		List<OrderProgram> orderProgramList = new ArrayList<>(); 
		OrderProgram orderProgram = OrderProgram.createOrderProgram(program, orderDto.getCount());
		orderProgramList.add(orderProgram);
		
		Order order = Order.createOrder(member, orderProgramList);
		orderRepositorty.save(order);
		
		return order.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
		
		List<Order> orders = orderRepositorty.findOrders(email, pageable); //주문 목록
		Long totalCount= orderRepositorty.countOrder(email); //총 주문 목록 갯수
		
		List<OrderHistDto> orderHistDtos = new ArrayList<>();
		
		for (Order order : orders) {
			OrderHistDto orderHistDto = new OrderHistDto(order);
			List<OrderProgram> orderItems = order.getOrderPrograms();
			
			for (OrderProgram orderItem : orderItems) {
				//상품의 대표 이미지
				ProgramImg itemImg = programImgRepositorty.findByProgramIdAndRepimgYn(orderItem.getProgram().getId(), "Y");
				OrderProgramDto orderItemDto = new OrderProgramDto(orderItem, itemImg.getImgUrl());
				orderHistDto.addOrderItemDto(orderItemDto);
			}
			
			orderHistDtos.add(orderHistDto);
		}
		
		return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
	}
	
	//현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 검사
	@Transactional(readOnly = true)
	public boolean validateOrder(Long orderId, String email) {
		Member curMember = memberRepository.findByEmail(email); //로그인한 사용자 찾기
		Order order = orderRepositorty.findById(orderId)
				                      .orElseThrow(EntityNotFoundException::new);
		Member savedMember = order.getMember(); //주문한 사용자 찾기
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		
		return true;
	}
	
	//주문 취소
	public void cancelOrder(Long orderId) {
		Order order = orderRepositorty.findById(orderId)
				                      .orElseThrow(EntityNotFoundException::new);
		order.cancelOrder();
	}
	
	//주문 삭제
	public void deleteOrder(Long orderId) {
		Order order = orderRepositorty.findById(orderId)
				                      .orElseThrow(EntityNotFoundException::new);
		orderRepositorty.delete(order);
	}
}