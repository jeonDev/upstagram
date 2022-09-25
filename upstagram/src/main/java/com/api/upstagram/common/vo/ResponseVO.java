package com.api.upstagram.common.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {

    public String code = "200";

    public String msg = "정상 처리 되었습니다.";

    public T data;
    
}
