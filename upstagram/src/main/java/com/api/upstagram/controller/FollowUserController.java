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
import com.api.upstagram.entity.FollowUser.FollowUserEntity;
import com.api.upstagram.service.FollowUserService;
import com.api.upstagram.vo.FollowUser.FollowUserPVO;
import com.api.upstagram.vo.FollowUser.FollowUserRVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FollowUserController {
    
    @Autowired
    private FollowUserService followService;
    
    /*
     * 팔로우 요청
     */
    @PostMapping("/user/add/follow")
    public ResponseVO<FollowUserRVO> requestFollow(@RequestBody FollowUserPVO pvo) {
        log.info("User Follow Request!");
        ResponseVO r = new ResponseVO<Object>();
        
        pvo.setId(CommonUtils.getUserId());

        FollowUserEntity entity = followService.requestFollow(pvo);
        FollowUserRVO rvo = FollowUserRVO.builder()
                                .followNo(entity.getFollowNo())
                                .id(entity.getId())
                                .followId(entity.getFollowId())
                                .build();

        r.setData(rvo);
        
        return r;
    }

    /*
     * Follow 리스트 조회
     */
    @GetMapping("/user/follow/list")
    public ResponseVO<List<FollowUserRVO>> getFollowList() {
        log.info("User Get FollowL ist!");
        ResponseVO r = new ResponseVO<Object>();

        FollowUserPVO pvo = new FollowUserPVO();
        pvo.setId(CommonUtils.getUserId());

        List<FollowUserRVO> rvo = followService.getFollowList(pvo).stream()
                                .map(m -> FollowUserRVO.builder()
                                            .followNo(m.getFollowNo())
                                            .id(m.getId())
                                            .followId(m.getFollowId())
                                            .build())
                                .collect(Collectors.toList());

        r.setData(rvo);
        
        return r;
    }
}
