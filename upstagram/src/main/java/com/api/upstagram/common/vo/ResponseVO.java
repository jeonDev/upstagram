package com.api.upstagram.common.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {

    public int code = Response.SUCCESS.getCode();

    public String message = Response.SUCCESS.getMessage();

    public T data;
    
}
