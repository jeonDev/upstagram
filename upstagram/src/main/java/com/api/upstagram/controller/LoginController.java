package com.api.upstagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.upstagram.entity.memberInfo.MemberInfoEntity;
import com.api.upstagram.service.LoginService;
import com.api.upstagram.vo.MemberInfoPVO;
import com.api.upstagram.vo.MemberInfoRVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    /*
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<MemberInfoRVO> join(@RequestBody MemberInfoPVO pvo) {
        log.info(this.getClass().getName() + " ==> join!");

        MemberInfoEntity entity = loginService.join(pvo);
        MemberInfoRVO rvo = new MemberInfoRVO();

        rvo.setId(entity.getId());
        rvo.setName(entity.getName());
        rvo.setSex(entity.getSex());
        rvo.setTel(entity.getTel());
        rvo.setOauthNo(entity.getOauthNo());
        
        return new ResponseEntity<>(rvo, HttpStatus.OK);
    }
}
