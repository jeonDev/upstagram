package com.api.upstagram.vo.MemberInfo;

import com.querydsl.core.annotations.QueryProjection;
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

    private Long followNo;

    @QueryProjection
    public MemberInfoRVO(String id, String name, String nickname, String sex, String tel, String oauthNo, String profileImage, Long followNo) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.tel = tel;
        this.oauthNo = oauthNo;
        this.profileImage = profileImage;
        this.followNo = followNo;
    }
}
