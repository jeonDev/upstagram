package com.api.upstagram.entity.Follow;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.upstagram.common.vo.BaseEntity;

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
    private String id;
    private String followId;

    @Builder
    private FollowUserEntity(String id, String followId) {
        this.id = id;
        this.followId = followId;
    }
}
