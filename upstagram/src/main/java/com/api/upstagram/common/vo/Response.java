package com.api.upstagram.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Response {

    SUCCESS("200", "정상 처리되었습니다."),
    ERROR("99", "처리 중 오류가 발생하였습니다."),
    ARGUMNET_ERROR("97", "입력값이 제대로 입력되지 않았습니다."),
    DELETE_ERROR("91", "삭제할 데이터가 존재하지 않습니다."),
    FOLLOW_ERROR("21", "팔로우 할 수 없는 대상입니다.")
    ;
    
    private final String code;
    private final String message;
}
