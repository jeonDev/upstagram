package com.api.upstagram.domain.OauthMemberInfo.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.upstagram.common.vo.Role;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 막기
@Table(name = "OAUTH_MEMBER_INFO")
@Entity
public class OauthMemberInfo {
    
    @Id
    private Long id;
    private String name;
    private String email;
    private String picture;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public OauthMemberInfo(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public OauthMemberInfo update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
