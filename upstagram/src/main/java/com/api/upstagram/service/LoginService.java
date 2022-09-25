package com.api.upstagram.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.upstagram.entity.memberInfo.MemberInfoEntity;
import com.api.upstagram.repository.MemberInfoRepository;
import com.api.upstagram.vo.MemberInfoPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    
    private final MemberInfoRepository memberInfoRepository;

    /*
     * 회원가입
     */
    public MemberInfoEntity join(MemberInfoPVO pvo) {
        log.info(this.getClass().getName() + " => join");

        this.validateIdCheck(pvo);

        MemberInfoEntity memberInfo = MemberInfoEntity.builder()
                                    .id(pvo.getId())
                                    .password(pvo.getPassword())
                                    .oauthNo("")        // TODO: OAuth 여부 체크
                                    .name(pvo.getName())
                                    .sex(pvo.getSex())
                                    .tel(pvo.getTel())
                                    .role("ROLE_USER")
                                    .tagAllowYn("Y")
                                    .pushViewYn("Y")
                                    .build();
        

        memberInfoRepository.save(memberInfo);

        return memberInfo;
    }

    public void validateIdCheck(MemberInfoPVO member) {
        log.info("회원가입 검증 시작");

        // 아이디 검증
        if(member.getId() == null) throw new IllegalArgumentException("아이디를 입력해주세요.");

        // 패스워드 검증 & 암호화 처리
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(member.getPassword() == null) throw new IllegalArgumentException("패스워드를 입력해주세요.");
        else member.setPassword(encoder.encode(member.getPassword()));

        // 전화번호 검증
        if(member.getTel() == null) throw new IllegalArgumentException("전화번호를 입력해주세요.");
        else {
            member.setTel(member.getTel().replaceAll("-", ""));
            if(member.getTel().length() != 11) throw new IllegalArgumentException("입력한 전화번호를 확인해주세요.");
        }

        if(member.getSex() == null) throw new IllegalArgumentException("성별을 입력해주세요.");

        Optional<MemberInfoEntity> entity = memberInfoRepository.findById(member.getId());

        if(entity.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 사용자입니다.");
        }
        log.info("회원가입 검증 종료");
    }
}
