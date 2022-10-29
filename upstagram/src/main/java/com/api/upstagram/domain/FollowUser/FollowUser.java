package com.api.upstagram.domain.FollowUser;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.api.upstagram.common.vo.BaseEntity;
import com.api.upstagram.domain.memberInfo.MemberInfo;

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
    
}
