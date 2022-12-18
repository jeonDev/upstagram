package com.api.upstagram.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.api.upstagram.vo.FollowUser.RecommandRVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.FollowUser.Entity.FollowUser;
import com.api.upstagram.service.FollowUserService;
import com.api.upstagram.vo.FollowUser.FollowUserPVO;
import com.api.upstagram.vo.FollowUser.FollowUserRVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FollowUserController {
    
    @Autowired
    private FollowUserService followUserService;
    
    /*
     * 팔로우 요청
     */
    @PostMapping("/user/follow/add")
    public ResponseVO<FollowUserRVO> requestFollow(@RequestBody FollowUserPVO pvo) {
        log.info(this.getClass().getName() + "User Follow Request!");
        ResponseVO<FollowUserRVO> r = new ResponseVO<FollowUserRVO>();
        
        pvo.setId(CommonUtils.getUserId());

        FollowUser entity = followUserService.requestFollowUser(pvo);

        FollowUserRVO rvo = FollowUserRVO.builder()
                                .followNo(entity.getFollowNo())
                                .id(entity.getIdMember().getId())
                                .followId(entity.getFollowMember().getId())
                                .build();
        
        r.setData(rvo);
        
        return r;
    }

    /*
    * 팔로우 제거
    * */
    @PostMapping("/user/follow/delete")
    public ResponseVO<FollowUserRVO> deleteFollow(@RequestBody FollowUserPVO pvo) {
        log.info(this.getClass().getName() + "User Follow Delete Request");
        ResponseVO<FollowUserRVO> r = new ResponseVO<FollowUserRVO>();
        
        followUserService.deleteFollowUser(pvo);

        return r;
    }

    /*
     * Follow 리스트 조회
     */
    @GetMapping("/user/follow/list")
    public ResponseVO<List<FollowUserRVO>> getFollowList() {
        log.info(this.getClass().getName() + "User Get Follow List!");
        ResponseVO<List<FollowUserRVO>> r = new ResponseVO<List<FollowUserRVO>>();

        FollowUserPVO pvo = new FollowUserPVO();
        pvo.setId(CommonUtils.getUserId());

        List<FollowUserRVO> rvo = followUserService.selectFollowUserList(pvo).stream()
                .map(m -> m.followUserToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);
        
        return r;
    }

    /*
     * Follower 리스트 조회
     */
    @GetMapping("/user/follower/list")
    public ResponseVO<List<FollowUserRVO>> getFollowerList() {
        log.info(this.getClass().getName() + "User Get Follower List!");
        ResponseVO<List<FollowUserRVO>> r = new ResponseVO<List<FollowUserRVO>>();

        FollowUserPVO pvo = new FollowUserPVO();
        pvo.setFollowId(CommonUtils.getUserId());

        List<FollowUserRVO> rvo = followUserService.selectFollowerUserList(pvo).stream()
                .map(m -> m.followUserToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);
        
        return r;
    }

    /*
    * Follow 추천 리스트 조회
    * */
    @GetMapping("/user/follow/recommand/list")
    public ResponseVO<List<RecommandRVO>> getFollowRecommandList() {
        log.info(this.getClass().getName() + " ==> Follow Recommander List!");
        ResponseVO<List<RecommandRVO>> r = new ResponseVO<List<RecommandRVO>>();

        FollowUserPVO pvo = new FollowUserPVO();
        pvo.setId(CommonUtils.getUserId());

        List<RecommandRVO> rvo = followUserService.selectFollowRecommanderList(pvo);

        // 추천 팔로우가 없을 경우, 팔로워가 가장 많은 사용자 추천
        if(rvo.size() == 0) {
            List<RecommandRVO> maxRvo = followUserService.selectFollowTopRecommanderList(pvo);

            r.setData(maxRvo);
        } else {
            r.setData(rvo);
        }

        return r;
    }
}
