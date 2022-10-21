package com.api.upstagram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.upstagram.entity.FollowUser.FollowUserEntity;
import com.api.upstagram.repository.FollowUserRepository;
import com.api.upstagram.vo.FollowUser.FollowUserPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowUserService {
    
    private final FollowUserRepository followRepository;

    /*
     * 팔로우 요청
     */
    public FollowUserEntity requestFollow(FollowUserPVO pvo){
        log.info(this.getClass().getName() + " ==> Request Follow");

        FollowUserEntity followUserEntity = FollowUserEntity.builder()
                                        .id(pvo.getId())
                                        .followId(pvo.getFollowId())
                                        .build();

        return followRepository.save(followUserEntity);
    }

    /*
     * 팔로우 리스트 조회
     */
    public List<FollowUserEntity> getFollowList(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Get Follow List");

        List<FollowUserEntity> followUserEntityList = followRepository.findById(pvo.getId());

        return followUserEntityList;
    }
}
