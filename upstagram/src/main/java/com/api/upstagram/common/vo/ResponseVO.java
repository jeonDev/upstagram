package com.api.upstagram.common.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {

    public String code = Response.SUCCESS.getCode();

    public String message = Response.SUCCESS.getMessage();

    public T data;
    
}
