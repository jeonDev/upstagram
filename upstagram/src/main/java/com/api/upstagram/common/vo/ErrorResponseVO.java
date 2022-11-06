package com.api.upstagram.common.vo;

import java.util.Date;

import com.api.upstagram.common.util.CommonUtils;

import lombok.Getter;

@Getter
public class ErrorResponseVO {

    private String code;
    private String message;
    private String timestamp;

    public ErrorResponseVO(String code, String message, String timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponseVO(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = CommonUtils.dateToYmdBarString(new Date());
    }

    public ErrorResponseVO(String code) {
        this.code = code;
        this.message = "오류가 발생하였습니다.";
        this.timestamp = CommonUtils.dateToYmdBarString(new Date());
    }
}
