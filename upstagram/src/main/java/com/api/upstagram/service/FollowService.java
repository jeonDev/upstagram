package com.api.upstagram.service;

import org.springframework.stereotype.Service;

import com.api.upstagram.entity.Follow.FollowUserEntity;
import com.api.upstagram.repository.FollowRepository;
import com.api.upstagram.vo.Follow.FollowPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    
    private final FollowRepository followRepository;

    /*
     * 팔로우 요청
     */
    public FollowUserEntity requestFollow(FollowPVO pvo){
        log.info(this.getClass().getName() + " ==> request Follow");

        FollowUserEntity followUserEntity = FollowUserEntity.builder()
                                        .id(pvo.getId())
                                        .followId(pvo.getFollowId())
                                        .build();

        return followRepository.save(followUserEntity);
    }
}
