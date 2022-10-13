package com.api.upstagram.entity.memberInfo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;

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
public class MemberInfoEntity extends BaseEntity{

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
        , String tel, String role, String pushViewYn, String tagAllowYn, Date joinDttm, String useYn){
            this.id = id;
            this.oauthNo = oauthNo;
            this.password = password;
            this.name = name;
            this.sex = sex;
            this.tel = tel;
            this.role = role;
            this.pushViewYn = pushViewYn;
            this.tagAllowYn = tagAllowYn;
            this.JoinDttm = joinDttm;
            this.useYn = useYn;
    }

    /* 로그인 성공 */
    public MemberInfoEntity loginSuccess(int wrongPasswordNumber, Date lastLoginDttm) {
        this.wrongPasswordNumber = wrongPasswordNumber;
        this.lastLoginDttm = lastLoginDttm;
        return this;
    }

    /* 로그인 실패 */
    public MemberInfoEntity loginFalse(int wrongPasswordNumber) {
        this.wrongPasswordNumber = wrongPasswordNumber;
        return this;
    }
}
