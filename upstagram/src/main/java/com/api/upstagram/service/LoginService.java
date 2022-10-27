package com.api.upstagram.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.security.Jwt.JwtTokenProvider;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.common.vo.Role;
import com.api.upstagram.common.vo.Token;
import com.api.upstagram.domain.memberInfo.MemberInfoEntity;
import com.api.upstagram.domain.memberInfo.MemberInfoHistoryEntity;
import com.api.upstagram.domain.memberInfo.MemberInfoHistoryRepository;
import com.api.upstagram.domain.memberInfo.MemberInfoRepository;
import com.api.upstagram.vo.MemberInfo.MemberInfoPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    
    private final MemberInfoRepository memberInfoRepository;

    private final MemberInfoHistoryRepository memberInfoHistoryRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder encoder;

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
                                    .nickname(pvo.getNickname())
                                    .sex(pvo.getSex())
                                    .tel(pvo.getTel())
                                    .role(Role.USER.getKey())
                                    .tagAllowYn("Y")
                                    .pushViewYn("Y")
                                    .joinDttm(LocalDateTime.now())
                                    .useYn("Y")
                                    .build();
        
        memberInfoRepository.save(memberInfo);

        MemberInfoHistoryEntity historyEntity = MemberInfoHistoryEntity.builder()
                                                .id(memberInfo.getId())
                                                .oauthNo(memberInfo.getOauthNo())
                                                .password(memberInfo.getPassword())
                                                .name(memberInfo.getName())
                                                .nickname(memberInfo.getNickname())
                                                .sex(memberInfo.getSex())
                                                .tel(memberInfo.getTel())
                                                .role(memberInfo.getRole())
                                                .pushViewYn(memberInfo.getPushViewYn())
                                                .tagAllowYn(memberInfo.getTagAllowYn())
                                                .joinDttm(memberInfo.getJoinDttm())
                                                .lastLoginDttm(memberInfo.getLastLoginDttm())
                                                .wrongPasswordNumber(memberInfo.getWrongPasswordNumber())
                                                .passwordChgDttm(memberInfo.getPasswordChgDttm())
                                                .useYn(memberInfo.getUseYn())
                                                .regDttm(memberInfo.getRegDttm())
                                                .build();
        
        memberInfoHistoryRepository.save(historyEntity);

        return memberInfo;
    }

    /* 회원가입 검증 */
    public void validateIdCheck(MemberInfoPVO member) {
        log.info("============== 회원가입 검증 시작 ==============");

        // 아이디 검증
        if(StringUtils.isNotEmpty(member.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"아이디를 입력해주세요.");

        // 패스워드 검증 & 암호화 처리
        if(StringUtils.isNotEmpty(member.getPassword())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"패스워드를 입력해주세요.");
        else member.setPassword(encoder.encode(member.getPassword()));

        // 전화번호 검증
        if(StringUtils.isNotEmpty(member.getTel())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"전화번호를 입력해주세요.");
        else {
            member.setTel(member.getTel().replaceAll("-", ""));
            if(member.getTel().length() != 11) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"입력한 전화번호를 확인해주세요.");
        }

        // 성별 검증 & 닉네임검증
        if(StringUtils.isNotEmpty(member.getSex())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "성별을 입력해주세요.");
        if(StringUtils.isNotEmpty(member.getNickname())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "닉네임을 입력해주세요.");
        /* TODO: 닉네임 검증(영어 + 특수문자? 기준 정해서 추가.) */

        Optional<MemberInfoEntity> nicknameCheck = memberInfoRepository.findByNickname(member.getNickname());
        if(nicknameCheck.isPresent()) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), "이미 사용중인 닉네임입니다.");

        Optional<MemberInfoEntity> entity = memberInfoRepository.findById(member.getId());

        if(entity.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"이미 등록된 사용자입니다.");

        log.info("============== 회원가입 검증 종료 ==============");
    }

    /* 로그인 */
    public Token login(MemberInfoPVO pvo) throws IllegalAccessException {

        // 파라미터 검증
        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"아이디를 입력해주세요.");
        if(StringUtils.isNotEmpty(pvo.getPassword())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"패스워드를 입력해주세요.");

        MemberInfoEntity loginEntity = memberInfoRepository.findByIdAndUseYn(pvo.getId(), "Y")
                .orElseThrow(() -> new IllegalAccessException("가입하지 않은 사용자입니다."));
        
        String password = loginEntity.getPassword();

        if(encoder.matches(pvo.getPassword(), password)) {

            // 패스워드 틀린횟수 체크
            if(loginEntity.getWrongPasswordNumber() >= 5) {
                log.info("패스워드 5회 이상 틀린 사용자 로그인 시도(" + loginEntity.getId() + ")");
                throw new IllegalAccessException("패스워드 5회 이상 틀리셨습니다.\n고객센터에 문의바랍니다.");
            }


            // 로그인 성공 시
            log.info("LOGIN Success(" + loginEntity.getId() + ")");

            loginEntity.loginSuccess( 0, LocalDateTime.now());

            memberInfoRepository.save(loginEntity);

            // Jwt Token 생성
            List<String> roles = new ArrayList<>();
            roles.add(loginEntity.getRole());
            Token token = jwtTokenProvider.generateJwtToken(loginEntity.getId(), roles);

            return token;
        } else {
            log.info("LOGIN Failed(" + loginEntity.getId() + ")");

            loginEntity.loginFalse(loginEntity.getWrongPasswordNumber() + 1);

            memberInfoRepository.save(loginEntity);
            
            throw new IllegalAccessException("비밀번호가 틀렸습니다.");
        }
    }

}
