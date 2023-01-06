package com.example.springsecurity.KIMJAEWOO;

public class MenuOrder {
     static Person person;
    public static void main(String[] args) {

             person = new Person("KIM",Menu.getCoffee());
             if(person.getMoney() > 0){
                 System.out.println("정상주문 \n 커피를 주문하셨습니다!");
                 System.out.println("잔액은 : "+person.getMoney()+"원 입니다!");
             }{
                   System.out.println("잔액이 부족합니다");
             }


    }
}
