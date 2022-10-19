package com.api.upstagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.entity.Follow.FollowUserEntity;
import com.api.upstagram.service.FollowService;
import com.api.upstagram.vo.Follow.FollowPVO;
import com.api.upstagram.vo.Follow.FollowRVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FollowController {
    
    @Autowired
    private FollowService followService;
    
    /*
     * 팔로우 요청
     */
    @PostMapping("/user/add/follow")
    public ResponseVO<Object> requestFollow(@RequestBody FollowPVO pvo) {
        log.info("User Follow Request!");
        ResponseVO r = new ResponseVO<Object>();
        
        FollowUserEntity entity = followService.requestFollow(pvo);
        FollowRVO rvo = new FollowRVO();
        rvo.setId(entity.getId());
        rvo.setFollowId(entity.getFollowId());

        r.setData(rvo);
        
        return r;
    }
}
