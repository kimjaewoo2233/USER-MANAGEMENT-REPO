package com.example.springsecurity.domain;

import lombok.Getter;

@Getter
public enum MemberRole {

    ROLE_NORMAL("ROLE_NORMAL"),
    ROLE_SILVER("ROLE_SILVER"),
    ROLE_GOLD("ROLE_GOLD"),
    ROLE_PLATINUM("ROLE_PLATINUM");

    String roleString;
    MemberRole(String roleString){
        this.roleString = roleString;

    }

}
