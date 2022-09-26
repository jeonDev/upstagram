package com.api.upstagram.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
public class UserSession {
    private String id;
    private String name;
    private String sex;
    private String tel;
    private String role;

    @Builder
    private UserSession(String id, String name, String sex, String tel, String role) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.role = role;
    }
}
