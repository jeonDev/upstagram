package com.api.upstagram.vo.MemberInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoRVO {
    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
    private String profileImage;
}
