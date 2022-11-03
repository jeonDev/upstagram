package com.api.upstagram.domain.MemberInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginHistoryId implements Serializable {
    private String id;                  // 아이디

    private LocalDateTime loginDttm;    // 로그인접속시간
}
