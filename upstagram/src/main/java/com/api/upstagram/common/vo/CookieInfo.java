package com.api.upstagram.common.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CookieInfo {
    private String cookieName;
    private String cookieValue;
    private int maxAge;
    private boolean httpOnly;
    private String path;
}
