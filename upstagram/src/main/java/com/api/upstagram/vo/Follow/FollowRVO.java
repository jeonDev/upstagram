package com.api.upstagram.vo.Follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowRVO {
    private Long followNo;
    private String id;
    private String followId;
}
