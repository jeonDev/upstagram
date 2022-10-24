package com.api.upstagram.vo.FollowUser;

import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowUserRVO {
    private Long followNo;
    private String id;
    private String followId;
    private MemberInfoRVO idMember;
    private MemberInfoRVO followMember;
}
