package com.api.upstagram.vo.MemberInfo;

import lombok.Data;

@Data
public class MemberInfoPVO {
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String sex;
    private String tel;

    private String role;
}
