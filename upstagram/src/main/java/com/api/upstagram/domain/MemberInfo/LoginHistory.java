package com.api.upstagram.domain.MemberInfo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 막기
@Table(name = "LOGIN_HISTORY")
@Entity
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyNo;

    private String id;                  // 아이디

    private LocalDateTime loginDttm;    // 로그인접속시간

    private String loginUri;            // 접속경로

    private String loginIp;             // 접속 IP

    /*
     * LoginHistoryEntity 생성
     */
    @Builder
    public LoginHistory(Long historyNo, String id, LocalDateTime loginDttm, String loginUri, String loginIp) {
        this.historyNo = historyNo;
        this.id = id;
        this.loginDttm = loginDttm;
        this.loginUri = loginUri;
        this.loginIp = loginIp;
    }
}
