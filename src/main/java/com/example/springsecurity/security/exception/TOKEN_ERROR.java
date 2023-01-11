package com.example.springsecurity.security.exception;

public enum TOKEN_ERROR {

    UNACCEPT(401, "TOKEN IS NULL OR TOO SHORT"),
    BADTYPE(401,"TOKEN TYPE BEARER"),
    MALFORM(403,"MALFORMED TOKEN"),
    BADSIGN(403,"BADSIGNATURED TOKEN"),
    EXPIRED(403,"EXPIRED TOKEN");

    private int status;
    private String msg;

    TOKEN_ERROR(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public int getStatus(){
        return  this.status;
    }

    public String getMsg(){
        return  this.msg;
    }
}

