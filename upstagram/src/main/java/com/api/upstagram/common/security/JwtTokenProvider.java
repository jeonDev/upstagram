package com.api.upstagram.common.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.api.upstagram.common.vo.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static long accessTokenValidTime = 30 * 60 * 1000L;
	private static long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

    private final Key accessSecretkey;
    private final Key refreshSecretkey;
    
    public JwtTokenProvider(@Value("${jwt.secret}") String accessSecretkey
        , @Value("${jwt.secret") String refreshSecretkey) {
            
        log.info("JWT Secret Key Decode!");
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecretkey);
        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretkey);

        this.accessSecretkey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshSecretkey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    /*
     * JWT Token 생성 
     */
    public Token generateAccessToken(String userEmail, List<String> roles) {

        log.info("JWT Token Generate!");
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roles);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)      // 정보저장
                .setIssuedAt(now)       // 토큰 발행시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))  // 토큰 유효시간 정보
                .signWith(accessSecretkey, SignatureAlgorithm.HS512)    // 암호화 알고리즘
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(refreshSecretkey, SignatureAlgorithm.HS512)
                .compact();

        return Token.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .key(userEmail)
            .build();
    }

    /*
     * 토큰 유효시간 체크
     */
    public boolean validDateToken(String token) {
        try{
            log.info("Token ValidDate Check!");
            Jwts.parserBuilder().setSigningKey(accessSecretkey).build().parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch(ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch(UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch(IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /*
     * 토큰에서 Authentication 정보 추출
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return null;
    }

    /*
     * 토큰에서 Claims 정보 추출
     */
    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(accessSecretkey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
