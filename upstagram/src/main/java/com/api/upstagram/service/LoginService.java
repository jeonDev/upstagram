package com.api.upstagram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.upstagram.common.security.JwtTokenProvider;
import com.api.upstagram.common.vo.Token;
import com.api.upstagram.entity.memberInfo.MemberInfoEntity;
import com.api.upstagram.repository.MemberInfoRepository;
import com.api.upstagram.vo.MemberInfo.MemberInfoPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    
    private final MemberInfoRepository memberInfoRepository;

    //private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /* 로그인 */
    public Token login(MemberInfoPVO pvo) throws IllegalAccessException {

        // 파라미터 검증
        if(pvo.getId() == null || "".equals(pvo.getId()) ) throw new IllegalArgumentException("아이디를 입력해주세요.");
        if(pvo.getPassword() == null || "".equals(pvo.getPassword()) ) throw new IllegalArgumentException("패스워드를 입력해주세요.");

        MemberInfoEntity entity = memberInfoRepository.findByIdAndUseYn(pvo.getId(), "Y");

        if(entity == null) throw new IllegalAccessException("가입하지 않은 사용자입니다.");
        
        String password = entity.getPassword();

        if(encoder.matches(pvo.getPassword(), password)) {

            // 패스워드 틀린횟수 체크
            if(entity.getWrongPasswordNumber() >= 5) {
                log.info("패스워드 5회 이상 틀린 사용자 로그인 시도(" + entity.getId() + ")");
                throw new IllegalAccessException("패스워드 5회 이상 틀리셨습니다.\n고객센터에 문의바랍니다.");
            }


            // 로그인 성공 시
            log.info("LOGIN Success(" + entity.getId() + ")");

            entity.loginSuccess( 0, new Date());

            memberInfoRepository.save(entity);

            // Jwt Token 생성
            List<String> roles = new ArrayList<String>();
            roles.add(entity.getRole());
            //Token token = jwtTokenProvider.generateAccessToken(entity.getId(), roles);

            //return token;
            return null;
        } else {
            log.info("LOGIN Failed(" + entity.getId() + ")");

            entity.loginFalse(entity.getWrongPasswordNumber() + 1);

            memberInfoRepository.save(entity);
            
            throw new IllegalAccessException("비밀번호가 틀렸습니다.");
        }
        
    }
    
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
                                    .joinDttm(new Date())
                                    .useYn("Y")
                                    .build();
        

        memberInfoRepository.save(memberInfo);

        return memberInfo;
    }

    /* 회원가입 검증 */
    public void validateIdCheck(MemberInfoPVO member) {
        log.info("회원가입 검증 시작");

        // 아이디 검증
        if(member.getId() == null) throw new IllegalArgumentException("아이디를 입력해주세요.");

        // 패스워드 검증 & 암호화 처리
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
