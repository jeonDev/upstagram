package com.api.upstagram.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.upstagram.common.vo.ResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FollowController {
    
    /*
     * 팔로우 요청
     */
    @PostMapping("/user/add/follow")
    public ResponseVO<Object> requestFollow() {
        log.info("User Follow Request!");
        ResponseVO r = new ResponseVO<Object>();
        
        return r;
    }
}
