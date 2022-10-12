package com.api.upstagram.entity.memberInfo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@DynamicUpdate
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

    private String useYn;

    /*
     * MemberInfo 생성
     */
    @Builder
    private MemberInfoEntity(String id, String oauthNo, String password, String name, String sex
        , String tel, String role, String pushViewYn, String tagAllowYn, Date joinDttm, int wrongPasswordNumber
        , Date passwordChgDttm, Date lastLoginDttm){
            this.id = id;
            this.oauthNo = oauthNo != null ? oauthNo : "" ;
            this.password = password;
            this.name = name;
            this.sex = sex;
            this.tel = tel;
            this.role = role != null ? role : "ROLE_USER";
            this.pushViewYn = pushViewYn != null ? pushViewYn : "Y";
            this.tagAllowYn = tagAllowYn != null ? tagAllowYn : "Y";
            this.JoinDttm = joinDttm;
            this.wrongPasswordNumber = wrongPasswordNumber;
            this.passwordChgDttm = passwordChgDttm;
            this.useYn = "Y";
            this.lastLoginDttm = lastLoginDttm;
    }
    
}
