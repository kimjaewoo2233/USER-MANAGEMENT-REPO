package com.example.springsecurity.KIMJAEWOO;

public class Person {

    private String name;
    private int money;

    Person(){
        this.money = 10000;
    }

    Person(String name,int money){

        this();
        this.name = name;
        this.money -= money;

    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }
}
