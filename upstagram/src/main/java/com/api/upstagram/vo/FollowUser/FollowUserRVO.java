package com.api.upstagram.vo.FollowUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowUserRVO {
    private Long followNo;
    private String id;
    private String followId;
}
