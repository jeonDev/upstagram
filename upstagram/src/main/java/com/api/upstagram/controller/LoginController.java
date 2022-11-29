package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.common.vo.Token;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.service.LoginService;
import com.api.upstagram.vo.MemberInfo.MemberInfoPVO;
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseVO<Token> login(@RequestBody MemberInfoPVO pvo
            , HttpServletRequest request
            , HttpServletResponse response) throws IllegalAccessException{
        log.info(this.getClass().getName() + " ==> login");

        ResponseVO<Token> r = new ResponseVO<Token>();

        Token token = loginService.login(pvo, request, response);

        r.setData(token);
        
        return r;
    }

    /*
    * 로그아웃
    * */
    @PostMapping("/user/logout")
    public ResponseVO<Void> logout(HttpServletResponse response) {
        log.info(this.getClass().getName() + " ==> Logout");
        ResponseVO<Void> r = new ResponseVO<Void>();

        loginService.logout(response);

        return r;
    }

    /*
    * Token 재발급
    * */
    @PostMapping("/token/re/issue")
    public ResponseVO<Token> tokenReIssue(@CookieValue("refreshToken") String refreshToken) {
        log.info(this.getClass().getName() + " ==> token 재 발급!");

        ResponseVO<Token> r = new ResponseVO<Token>();

        Token token = loginService.tokenReIssue(refreshToken);

        r.setData(token);

        return r;

    }

    /*
    * 사용자 정보 수정
    * */
    @PostMapping("/user/info/update")
    public ResponseVO<MemberInfoRVO> memberInfoUpdate(@RequestBody MemberInfoPVO pvo) throws IllegalAccessException {
        log.info(this.getClass().getName() + " ==> Member Info Update");

        ResponseVO<MemberInfoRVO> r = new ResponseVO<MemberInfoRVO>();
        pvo.setId(CommonUtils.getUserId());

        MemberInfoRVO rvo = loginService.memberInfoUpdate(pvo).memberInfoToRVO();

        r.setData(rvo);

        return r;
    }

    /*
    * 사용자 조회
    * */
    @GetMapping("/admin/member/list")
    public ResponseVO<List<MemberInfoRVO>> memberInfoList(@RequestParam(required = false) String role) {
        log.info(this.getClass().getName() + " ==> Member Info list");

        ResponseVO<List<MemberInfoRVO>> r = new ResponseVO<List<MemberInfoRVO>>();

        List<MemberInfoRVO> rvo = loginService.selectMemberInfoList().stream()
                .map(m -> m.memberInfoToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);

        return r;
    }

    /*
     * 사용자 조회
     * */
    @GetMapping("/user/info/get")
    public ResponseVO<MemberInfoRVO> getUserInfo() {
        log.info(this.getClass().getName() + " ==> Get User Info");
        MemberInfoPVO pvo = new MemberInfoPVO();
        pvo.setId(CommonUtils.getUserId());
        ResponseVO<MemberInfoRVO> r = new ResponseVO<MemberInfoRVO>();

        MemberInfoRVO rvo = loginService.selectMemberInfo(pvo).memberInfoToRVO();

        r.setData(rvo);

        return r;
    }
}
