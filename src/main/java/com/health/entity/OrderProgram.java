package com.health.entity;

import javax.persistence.*;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_program") //테이블명
@Getter
@Setter
@ToString
public class OrderProgram extends BaseEntity {
	
	@Id
	@Column(name = "order_program_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Program program;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	private int orderPrice; //주문 가격
	
	private int count; //주문수량
	
	//주문할 상품과 주문 수량을 통해 orderItem객체를 만듬
	public static OrderProgram createOrderProgram(Program program, int count) {
		OrderProgram orderProgram = new OrderProgram();
		orderProgram.setProgram(program);
		orderProgram.setCount(count);
		orderProgram.setOrderPrice(program.getPrice());
		
		return orderProgram;
	}
	
	//주문한 총 가격
	public int getTotalPrice() {
		return orderPrice*count;
	}
	
	public void cancel() {
		this.getProgram().addStock(count);
	}
	
}