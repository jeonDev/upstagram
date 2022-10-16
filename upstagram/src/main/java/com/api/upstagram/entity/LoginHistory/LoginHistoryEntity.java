package com.api.upstagram.entity.LoginHistory;

import java.io.Serializable;
import java.util.Date;

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
@IdClass(LoginHistoryEntity.class)
public class LoginHistoryEntity implements Serializable {
    
    @Id
    private String id;                  // 아이디

    @Id
    private Date loginDttm;             // 로그인접속시간

    private String loginUri;            // 접속경로

    private String loginNowUrl;         // 현재 URL

    private String loginIp;             // 접속 IP

    /*
     * LoginHistoryEntity 생성
     */
    @Builder
    private LoginHistoryEntity(String id, String loginUri, String loginDttm, String loginNowUrl
        , String loginIp){
            this.id = id;
            this.loginUri = loginUri;
            this.loginDttm = new Date();
            this.loginNowUrl = loginNowUrl;
            this.loginIp = loginIp;
    }

}
