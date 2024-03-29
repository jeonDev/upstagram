package com.api.upstagram.domain.MemberInfo.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.domain.FollowUser.Entity.FollowUser;
import com.api.upstagram.vo.MemberInfo.MemberInfoPVO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.domain.BaseEntity;
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

    private String profileImage;

    @OneToMany(mappedBy = "idMember")
    private List<FollowUser> idUser;

    @OneToMany(mappedBy = "followMember")
    private List<FollowUser> followUser;

    /*
     * MemberInfo 생성
     */
    @Builder
    public MemberInfo(String id, String oauthNo, String password, String name, String nickname, String sex,
            String tel, String role, String pushViewYn, String tagAllowYn, LocalDateTime joinDttm, LocalDateTime lastLoginDttm,
            int wrongPasswordNumber, LocalDateTime passwordChgDttm, String useYn, String profileImage) {
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
        this.profileImage = profileImage;
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

    public MemberInfo updateMemberInfo(MemberInfoPVO pvo) {
        if(!StringUtils.isNotEmpty(pvo.getNewPassword())) this.password = pvo.getNewPassword();
        if(!StringUtils.isNotEmpty(pvo.getName())) this.name = pvo.getName();
        if(!StringUtils.isNotEmpty(pvo.getNickname())) this.nickname = pvo.getNickname();
        if(!StringUtils.isNotEmpty(pvo.getSex())) this.sex = pvo.getSex();
        if(!StringUtils.isNotEmpty(pvo.getTel())) this.tel = pvo.getTel();
        if(!StringUtils.isNotEmpty(pvo.getRole())) this.role = pvo.getRole();
        if(!StringUtils.isNotEmpty(pvo.getUseYn())) this.useYn = pvo.getUseYn();
        if(!StringUtils.isNotEmpty(pvo.getProfileImage())) this.profileImage = pvo.getProfileImage();
        return this;
    }

    public MemberInfoRVO memberInfoToRVO() {
        return MemberInfoRVO.builder()
                .id(this.getId())
                .name(this.getName())
                .nickname(this.getNickname())
                .oauthNo(this.getOauthNo())
                .sex(this.getSex())
                .tel(this.getTel())
                .profileImage(this.profileImage)
                .build();
    }
}
