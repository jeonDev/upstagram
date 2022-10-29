package com.api.upstagram.domain.memberInfo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 막기
@Table(name = "LOGIN_HISTORY")
@Entity
@IdClass(LoginHistory.class)
public class LoginHistory implements Serializable {
    
    @Id
    private String id;                  // 아이디

    @Id
    private LocalDateTime loginDttm;             // 로그인접속시간

    private String loginUri;            // 접속경로

    private String loginNowUrl;         // 현재 URL

    private String loginIp;             // 접속 IP

    /*
     * LoginHistoryEntity 생성
     */
    @Builder
    private LoginHistory(String id, String loginUri, String loginDttm, String loginNowUrl
        , String loginIp){
            this.id = id;
            this.loginUri = loginUri;
            this.loginDttm = LocalDateTime.now();
            this.loginNowUrl = loginNowUrl;
            this.loginIp = loginIp;
    }

}
