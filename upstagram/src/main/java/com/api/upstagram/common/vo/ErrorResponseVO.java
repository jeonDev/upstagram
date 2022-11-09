package com.api.upstagram.common.vo;

import java.util.Date;

import com.api.upstagram.common.util.CommonUtils;

import lombok.Getter;

@Getter
public class ErrorResponseVO {

    private int code;
    private String message;
    private String timestamp;

    public ErrorResponseVO(int code, String message, String timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponseVO(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = CommonUtils.format(new Date(), "yyyy-MM-dd");
    }

    public ErrorResponseVO(int code) {
        this.code = code;
        this.message = "오류가 발생하였습니다.";
        this.timestamp = CommonUtils.format(new Date(), "yyyy-MM-dd");
    }
}
