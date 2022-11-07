package com.api.upstagram.vo.FollowUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommandRVO {

    private Long followNo;
    private String followId;
    private String followName;
    private String followNickname;
    private String followSex;
    private String followTel;
    private String followOauthNo;

    private int friendCnt;
}
