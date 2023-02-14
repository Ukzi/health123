package com.health.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.health.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="orders") //테이블명
@Getter
@Setter
@ToString
public class Order {
	
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	private LocalDateTime orderDate; //주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; //주문상태
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //OrderItem에 있는 order에 의해 관리가 된다.
	private List<OrderProgram> orderPrograms = new ArrayList<>();
	
	public void addOrderProgram(OrderProgram orderProgram) {
		orderPrograms.add(orderProgram);
		orderProgram.setOrder(this); //★양방향 참조관계 일때는 orderItem객체에도 order객체를 세팅한다.
	}
	
	//order 객체를 생성해 준다.
	public static Order createOrder(Member member, List<OrderProgram> orderProgrmaList) {
		Order order = new Order();
		order.setMember(member);
		
		for(OrderProgram orderProgram : orderProgrmaList) {
			order.addOrderProgram(orderProgram);
		}
		
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	//총 주문금액
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderProgram orderProgram : orderPrograms) {
			totalPrice += orderProgram.getTotalPrice();
		}
		return totalPrice;
	}
	
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCEL; //상태 변경
		
		for(OrderProgram orderProgram : orderPrograms) {
			orderProgram.cancel(); //재고 증가
		}
	}
	
}
