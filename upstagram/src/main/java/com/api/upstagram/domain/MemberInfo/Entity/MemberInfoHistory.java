package com.api.upstagram.domain.MemberInfo.Entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "MEMBER_INFO_HISTORY")
@Entity
public class MemberInfoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyNo;

    private String id;

    private String oauthNo;

    private String password;

    private String name;

    private String nickname;
    
    private String sex;
    
    private String tel;

    private String role;

    private String pushViewYn;

    private String tagAllowYn;

    private LocalDateTime joinDttm;

    private LocalDateTime lastLoginDttm;

    private int wrongPasswordNumber;

    private LocalDateTime passwordChgDttm;

    private String useYn;

    private LocalDateTime regDttm;

    @Builder
    public MemberInfoHistory(Long historyNo, String id, String oauthNo, String password, String name,
            String nickname, String sex, String tel, String role, String pushViewYn, String tagAllowYn, LocalDateTime joinDttm,
            LocalDateTime lastLoginDttm, int wrongPasswordNumber, LocalDateTime passwordChgDttm, String useYn, LocalDateTime regDttm) {
        this.historyNo = historyNo;
        this.id = id;
        this.oauthNo = oauthNo;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.tel = tel;
        this.role = role;
        this.pushViewYn = pushViewYn;
        this.tagAllowYn = tagAllowYn;
        this.joinDttm = joinDttm;
        this.lastLoginDttm = lastLoginDttm;
        this.wrongPasswordNumber = wrongPasswordNumber;
        this.passwordChgDttm = passwordChgDttm;
        this.useYn = useYn;
        this.regDttm = regDttm;
    }

}
