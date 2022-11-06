package com.api.upstagram.vo.FollowUser;

import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowUserRVO {
    private Long followNo;

    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
    // Follow Member
    private String followId;
    private String followName;
    private String followNickname;
    private String followSex;
    private String followTel;
    private String followOauthNo;
}
