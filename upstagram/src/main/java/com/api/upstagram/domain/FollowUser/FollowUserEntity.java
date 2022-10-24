package com.api.upstagram.domain.FollowUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.api.upstagram.common.vo.BaseEntity;
import com.api.upstagram.domain.memberInfo.MemberInfoEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 막기
@Table(name = "FOLLOW_USER")
@Entity
public class FollowUserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followNo;
    
    @ManyToOne
    @JoinColumn(name = "ID")
    private MemberInfoEntity idMember;

    @ManyToOne
    @JoinColumn(name = "FOLLOW_ID")
    private MemberInfoEntity followMember;

    @Builder
    public FollowUserEntity(Long followNo, MemberInfoEntity idMember, MemberInfoEntity followMember) {
        this.followNo = followNo;
        this.idMember = idMember;
        this.followMember = followMember;
    }
    
}
