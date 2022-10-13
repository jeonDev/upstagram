package com.api.upstagram.common.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String accessSecretKey = "accessKey321";
    private String refreshSecretKey = "refreshKey432";

    private long accessTokenValidTime = 30 * 60 * 1000L;
	private long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    
}
