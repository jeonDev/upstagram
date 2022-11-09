package com.api.upstagram.domain.FollowUser;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.MemberInfo;

import com.api.upstagram.vo.FollowUser.FollowUserRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 막기
@Table(name = "FOLLOW_USER")
@Entity
public class FollowUser extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followNo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo idMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOW_ID")
    private MemberInfo followMember;

    @Builder
    public FollowUser(Long followNo, MemberInfo idMember, MemberInfo followMember) {
        this.followNo = followNo;
        this.idMember = idMember;
        this.followMember = followMember;
    }

    public FollowUserRVO followUserToRVO() {
        return FollowUserRVO.builder()
                .followNo(this.followNo)
                .id(this.idMember.getId())
                .name(this.idMember.getName())
                .nickname(this.idMember.getNickname())
                .sex(this.idMember.getSex())
                .tel(this.idMember.getTel())
                .oauthNo(this.idMember.getOauthNo())
                .followId(this.followMember.getId())
                .followName(this.followMember.getName())
                .followNickname(this.followMember.getNickname())
                .followSex(this.followMember.getSex())
                .followTel(this.followMember.getTel())
                .followOauthNo(this.followNo.toString())
                .build();
    }
}
