package com.api.upstagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.entity.FollowUser.FollowUserEntity;
import com.api.upstagram.repository.FollowUserRepository;
import com.api.upstagram.vo.FollowUser.FollowUserPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowUserService {
    
    private final FollowUserRepository followUserRepository;

    /*
     * 팔로우 요청
     */
    public FollowUserEntity requestFollowUser(FollowUserPVO pvo){
        log.info(this.getClass().getName() + " ==> Request Follow User");

        FollowUserEntity followUserEntity = FollowUserEntity.builder()
                                        .id(pvo.getId())
                                        .followId(pvo.getFollowId())
                                        .build();

        return followUserRepository.save(followUserEntity);
    }

    /*
     * 팔로우 제거
     */
    public void deleteFollowUser(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Delete Follow User");

        Optional<FollowUserEntity> followUserEntity = followUserRepository.findByFollowNo(pvo.getFollowNo());
        if(followUserEntity.isPresent()) {
            followUserRepository.delete(followUserEntity.get());
        } else {
            throw new CustomException("99", "삭제할 데이터가 존재하지 않습니다.");
        }
        
    }

    /*
     * 팔로우 리스트 조회
     */
    public List<FollowUserEntity> getFollowUserList(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Get Follow User List");

        List<FollowUserEntity> followUserEntityList = followUserRepository.findById(pvo.getId());

        return followUserEntityList;
    }
}
