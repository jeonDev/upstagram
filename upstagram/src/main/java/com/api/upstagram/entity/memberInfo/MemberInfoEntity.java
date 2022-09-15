package com.api.upstagram.entity.memberInfo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 막기
@Table(name = "MEMBER_INFO")
@Entity
public class MemberInfoEntity {

    @Id
    private String id;

    private String oauthNo;

    private String password;

    private String name;

    private String sex;
    
    private String tel;

    private String role;

    private String pushViewYn;

    private String tagAllowYn;

    private Date JoinDttm;

    private Date lastLoginDttm;

    private int wrongPasswordNumber;

    private Date passwordChgDttm;

    /*
     * MemberInfo 생성
     */
    @Builder
    private MemberInfoEntity(String id, String oauthNo, String password, String name, String sex
        , String tel, String role, String pushViewYn, String tagAllowYn){
            this.id = id;
            this.oauthNo = oauthNo != null ? oauthNo : "ROLE_USER" ;
            this.password = password;       // 패스워드 암호화 필요!
            this.name = name;
            this.sex = sex;
            this.tel = tel;
            this.role = role;
            this.pushViewYn = pushViewYn;
            this.tagAllowYn = tagAllowYn;
            this.JoinDttm = new Date();
            this.wrongPasswordNumber = 0;
            this.passwordChgDttm = new Date();
    }
}
