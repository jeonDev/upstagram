package com.api.upstagram.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookieInfo {
    private String cookieName;
    private String cookieValue;
    private int maxAge;
    private boolean httpOnly;
    private String path;

    public CookieInfo(String refreshToken) {
        this.cookieName = "refreshToken";
        this.cookieValue = refreshToken;
        this.maxAge = 7 * 24 * 60 * 60 * 1000;
        this.httpOnly = true;
        this.path = "/";
    }
}
