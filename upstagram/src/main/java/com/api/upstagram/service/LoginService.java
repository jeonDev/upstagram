package com.api.upstagram.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.api.upstagram.entity.memberInfo.MemberInfoEntity;
import com.api.upstagram.repository.MemberInfoRepository;
import com.api.upstagram.vo.MemberInfoPVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final MemberInfoRepository memberInfoRepository;

    /*
     * 회원가입
     */
    public MemberInfoEntity join(MemberInfoPVO pvo) {
        
        MemberInfoEntity memberInfo = MemberInfoEntity.builder()
                                    .id(pvo.getId())
                                    .password(pvo.getPassword())
                                    .oauthNo("")        // TODO: OAuth 여부 체크
                                    .name(pvo.getName())
                                    .sex(pvo.getSex())
                                    .tel(pvo.getTel())
                                    .role("ROLE_USER")      // TODO: ROLE_ADMIN을 입력할 수 있게할지?
                                    .tagAllowYn("Y")
                                    .pushViewYn("Y")
                                    .build();

        memberInfoRepository.save(memberInfo);

        return memberInfo;
    }
}
