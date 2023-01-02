package com.rezztoran.rezztoranbe.enums;

public enum ReturnType {

    SUCCESS(0,"Success"),
    FAILURE(-1,"Failure");

    int code;
    String message;

    ReturnType(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
