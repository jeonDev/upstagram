package com.api.upstagram.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Response {

    SUCCESS("200", "정상 처리되었습니다."),
    ERROR("99", "처리 중 오류가 발생하였습니다.");

    private final String code;
    private final String message;
}
