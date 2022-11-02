package com.api.upstagram.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.FollowUser.FollowUser;
import com.api.upstagram.domain.FollowUser.FollowUserRepository;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.FollowUser.FollowUserInterface;
import com.api.upstagram.vo.FollowUser.FollowUserPVO;
import com.api.upstagram.vo.FollowUser.FollowUserRVO;
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

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
    public FollowUser requestFollowUser(FollowUserPVO pvo){
        log.info(this.getClass().getName() + " ==> Request Follow User");

        // 입력값 검증 (1. 팔로우 ID와 내 ID 비교)
        if(pvo.getId().equals(pvo.getFollowId())) {
            throw new CustomException(Response.FOLLOW_ERROR.getCode(), Response.FOLLOW_ERROR.getMessage());
        }

        MemberInfo idMember = MemberInfo.builder()
                                        .id(pvo.getId())
                                        .build();
        
        MemberInfo followMember = MemberInfo.builder()
                                        .id(pvo.getFollowId())
                                        .build();

        FollowUser followUserEntity = FollowUser.builder()
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
        
        Optional<FollowUser> followUserEntity = followUserRepository.findByFollowNo(pvo.getFollowNo());
        if(followUserEntity.isPresent()) {
            
            FollowUser entity = followUserEntity.get();
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
    public List<FollowUserRVO> getFollowUserList(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Get Follow User List");
        
        List<FollowUserRVO> list = this.getFollowUserImpl(pvo).stream()
                                    .map(m -> FollowUserRVO.builder()
                                            .followNo(m.getFollowNo())
                                            .idMember(MemberInfoRVO.builder()
                                                .id(m.getId())
                                                .name(m.getName())
                                                .nickname(m.getNickname())
                                                .sex(m.getSex())
                                                .tel(m.getTel())
                                                .oauthNo(m.getOauthNo())
                                                .build())
                                            .followMember(MemberInfoRVO.builder()
                                                .id(m.getFollowId())
                                                .name(m.getFollowName())
                                                .nickname(m.getFollowNickname())
                                                .sex(m.getFollowSex())
                                                .tel(m.getFollowTel())
                                                .oauthNo(m.getFollowOauthNo())
                                                .build())
                                            .build())
                                    .collect(Collectors.toList());

        return list;
    }

    /* Follow 조회 */
    public List<FollowUserInterface> getFollowUserImpl(FollowUserPVO pvo) {
        return followUserRepository.findByFollowUserList(pvo.getId());
    }

    /*
     * 팔로워 리스트 조회
     */
    public List<FollowUserRVO> getFollowerUserList(FollowUserPVO pvo) {
        log.info(this.getClass().getName() + " ==> Get Follow User List");

        List<FollowUserRVO> list = this.getFollowerUserImpl(pvo).stream()
                                    .map(m -> FollowUserRVO.builder()
                                            .followNo(m.getFollowNo())
                                            .idMember(MemberInfoRVO.builder()
                                                .id(m.getId())
                                                .name(m.getName())
                                                .nickname(m.getNickname())
                                                .sex(m.getSex())
                                                .tel(m.getTel())
                                                .oauthNo(m.getOauthNo())
                                                .build())
                                            .followMember(MemberInfoRVO.builder()
                                                .id(m.getFollowId())
                                                .name(m.getFollowName())
                                                .nickname(m.getFollowNickname())
                                                .sex(m.getFollowSex())
                                                .tel(m.getFollowTel())
                                                .oauthNo(m.getFollowOauthNo())
                                                .build())
                                            .build())
                                    .collect(Collectors.toList());

        return list;
    }

    /* Follower 조회 */
    public List<FollowUserInterface> getFollowerUserImpl(FollowUserPVO pvo) {
        return followUserRepository.findByFollowerUserList(pvo.getFollowId());
    }
}