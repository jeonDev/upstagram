package com.api.upstagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.common.vo.Token;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.service.LoginService;
import com.api.upstagram.vo.MemberInfo.MemberInfoPVO;
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseVO<MemberInfoRVO> join(@RequestBody(required = false) MemberInfoPVO pvo) {
        log.info(this.getClass().getName() + " ==> join");

        ResponseVO<MemberInfoRVO> r = new ResponseVO<MemberInfoRVO>();

        MemberInfoRVO rvo = loginService.join(pvo).memberInfoToRVO();

        r.setData(rvo);

        return r;
    }

    /*
     * 로그인
     */
    @PostMapping("/login")
    public ResponseVO<Token> login(@RequestBody MemberInfoPVO pvo, HttpServletRequest request) throws IllegalAccessException{
        log.info(this.getClass().getName() + " ==> login");

        ResponseVO<Token> r = new ResponseVO<Token>();

        Token token = loginService.login(pvo, request);

        r.setData(token);
        
        return r;
    }
}
