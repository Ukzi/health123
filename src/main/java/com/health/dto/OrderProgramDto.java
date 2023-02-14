package com.health.dto;


import com.health.entity.OrderProgram;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProgramDto {

    public OrderProgramDto(OrderProgram orderProgram, String imgUrl){
        this.ProgramNm = orderProgram.getProgram().getProgramNm();
        this.count = orderProgram.getCount();
        this.orderPrice = orderProgram.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String ProgramNm; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로

}
