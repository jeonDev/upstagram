package com.api.upstagram.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.common.vo.User;
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
    public ResponseVO<FollowRVO> requestFollow(@RequestBody FollowPVO pvo) {
        log.info("User Follow Request!");
        ResponseVO r = new ResponseVO<Object>();
        
        pvo.setId(CommonUtils.getUserId());

        FollowUserEntity entity = followService.requestFollow(pvo);
        FollowRVO rvo = new FollowRVO(entity.getFollowNo(), entity.getId(), entity.getFollowId());

        r.setData(rvo);
        
        return r;
    }

    /*
     * Follow 리스트 조회
     */
    @GetMapping("/user/follow/list")
    public ResponseVO<List<FollowRVO>> getFollowList() {
        log.info("User Get FollowL ist!");
        ResponseVO r = new ResponseVO<Object>();

        FollowPVO pvo = new FollowPVO();
        pvo.setId(CommonUtils.getUserId());

        List<FollowRVO> rvo = followService.getFollowList(pvo).stream()
                                .map(m -> new FollowRVO(m.getFollowNo(), m.getId(), m.getFollowId()))
                                .collect(Collectors.toList());

        r.setData(rvo);
        
        return r;
    }
}
