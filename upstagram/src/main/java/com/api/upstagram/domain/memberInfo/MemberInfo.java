package com.api.upstagram.domain.memberInfo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

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
public class MemberInfo extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String oauthNo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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

    /*
     * MemberInfo 생성
     */
    @Builder
    public MemberInfo(String id, String oauthNo, String password, String name, String nickname, String sex,
            String tel, String role, String pushViewYn, String tagAllowYn, LocalDateTime joinDttm, LocalDateTime lastLoginDttm,
            int wrongPasswordNumber, LocalDateTime passwordChgDttm, String useYn) {
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
    }
    
    /* 로그인 성공 */
    public MemberInfo loginSuccess() {
        this.wrongPasswordNumber = 0;
        this.lastLoginDttm = LocalDateTime.now();
        return this;
    }

    /* 로그인 실패 */
    public MemberInfo loginFalse() {
        this.wrongPasswordNumber+= 1;
        return this;
    }

    public MemberInfoRVO memberEntityToRVO() {
        return MemberInfoRVO.builder()
                .id(this.getId())
                .name(this.getName())
                .nickName(this.getNickname())
                .oauthNo(this.getOauthNo())
                .sex(this.getSex())
                .tel(this.getTel())
                .build();
    }
}
