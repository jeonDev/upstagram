package com.api.upstagram.common.security.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_TYPE = "bearer";
    private static long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;
	private static long REFRESH_TOKEN_VALID_TIME = 7 * 24 * 60 * 60 * 1000L;
    private final Key secretkey;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        log.info("JWT Secret Key Decode!" + secret);
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.secretkey = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * JWT Token 생성 
     */
    public Token generateAccessToken(String userId, List<String> roles) {

        log.info("JWT Token Generate!" + userId);
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)      // 정보저장
                .setIssuedAt(now)       // 토큰 발행시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))  // 토큰 유효시간 정보
                .signWith(secretkey, SignatureAlgorithm.HS512)    // 암호화 알고리즘
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(secretkey, SignatureAlgorithm.HS512)
                .compact();

        return Token.builder()
            .grantType(BEARER_TYPE)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .key(userId)
            .build();
    }

    /*
     * 토큰 유효시간 체크
     */
    public boolean validDateToken(String token) {
        try{
            log.info("Token ValidDate Check!");
            Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(token);
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
        UserDetails userDetails = userDetailService.loadUserByUsername(this.parseClaims(accessToken).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }

    /*
     * 토큰에서 Claims 정보 추출
     */
    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /* 
     * Request의 Header에서 Token 값 추출. "Authrization" : "TOKEN값"
     */
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
