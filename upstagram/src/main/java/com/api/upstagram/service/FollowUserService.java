package com.api.upstagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.FollowUser.FollowUserEntity;
import com.api.upstagram.domain.FollowUser.FollowUserRepository;
import com.api.upstagram.domain.memberInfo.MemberInfoEntity;
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

        // 입력값 검증 (1. 팔로우 ID와 내 ID 비교)
        if(pvo.getId().equals(pvo.getFollowId())) {
            throw new CustomException(Response.FOLLOW_ERROR.getCode(), Response.FOLLOW_ERROR.getMessage());
        }

        MemberInfoEntity idMember = MemberInfoEntity.builder()
                                        .id(pvo.getId())
                                        .build();
        
        MemberInfoEntity followMember = MemberInfoEntity.builder()
                                        .id(pvo.getFollowId())
                                        .build();

        FollowUserEntity followUserEntity = FollowUserEntity.builder()
                                        .idMember(idMember)
                                        .followMember(followMember)
                                        .build();
        
        try {
            followUserRepository.save(followUserEntity);
            
            // TODO: Push & History 저장
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(Response.FOLLOW_ERROR.getCode(), Response.FOLLOW_ERROR.getMessage());
        }

        return followUserEntity;
    }

    /*
     * 팔로우 제거
     */
    public void deleteFollowUser(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Delete Follow User");
        
        Optional<FollowUserEntity> followUserEntity = followUserRepository.findByFollowNo(pvo.getFollowNo());
        if(followUserEntity.isPresent()) {
            
            FollowUserEntity entity = followUserEntity.get();
            if(!CommonUtils.getUserId().equals(entity.getIdMember().getId())) throw new CustomException(Response.NOT_SELF_ERROR.getCode(), Response.NOT_SELF_ERROR.getMessage());
            log.info(entity.getIdMember() + " <=> " + entity.getFollowMember().getId() + " DELETE!");

            followUserRepository.delete(entity);

        } else {
            throw new CustomException(Response.DELETE_ERROR.getCode(), Response.DELETE_ERROR.getMessage());
        }

    }

    /*
     * 팔로우 리스트 조회
     */
    public List<FollowUserEntity> getFollowUserList(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Get Follow User List");

        MemberInfoEntity idMember = MemberInfoEntity.builder()
                                    .id(pvo.getId())
                                    .build();

        List<FollowUserEntity> followUserEntityList = followUserRepository.findByIdMember(idMember);

        return followUserEntityList;
    }

    /*
     * 팔로워 리스트 조회
     */
    public List<FollowUserEntity> getFollowerUserList(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Get Follow User List");

        MemberInfoEntity followMember = MemberInfoEntity.builder()
                                    .id(pvo.getFollowId())
                                    .build();

        List<FollowUserEntity> followUserEntityList = followUserRepository.findByFollowMember(followMember);

        return followUserEntityList;
    }
}
