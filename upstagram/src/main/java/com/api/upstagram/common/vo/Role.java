package com.api.upstagram.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    MANAGE("ROLE_MANAGE", "광고 담당자");

    private final String key;
    private final String title;
}
