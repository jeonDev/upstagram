package com.api.upstagram.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.CookieInfo;
import com.api.upstagram.common.vo.Role;
import com.api.upstagram.domain.MemberInfo.Entity.LoginHistory;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfoHistory;
import com.api.upstagram.domain.MemberInfo.Repository.LoginHistoryRepository;
import com.api.upstagram.domain.MemberInfo.Repository.MemberInfoDslRepository;
import com.api.upstagram.domain.MemberInfo.Repository.MemberInfoHistoryRepository;
import com.api.upstagram.domain.MemberInfo.Repository.MemberInfoRepository;
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;
import com.api.upstagram.vo.Search.SearchPVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.security.Jwt.JwtTokenProvider;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.common.vo.Token;
import com.api.upstagram.vo.MemberInfo.MemberInfoPVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    
    private final MemberInfoRepository memberInfoRepository;

    private final MemberInfoHistoryRepository memberInfoHistoryRepository;

    private final MemberInfoDslRepository memberInfoDslRepository;

    private final LoginHistoryRepository loginHistoryRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /*
     * 회원가입
     */
    public MemberInfo join(MemberInfoPVO pvo) {
        log.info(this.getClass().getName() + " => join");

        // 1. 회원가입 검증
        this.validateIdCheck(pvo);

        // 2. MemberInfo Entity 세팅
        MemberInfo memberInfo = MemberInfo.builder()
                                    .id(pvo.getId())
                                    .password(pvo.getPassword())
                                    .oauthNo("")        // TODO: OAuth 여부 체크
                                    .name(pvo.getName())
                                    .nickname(pvo.getNickname())
                                    .sex(pvo.getSex())
                                    .tel(pvo.getTel())
                                    .role(Role.USER.getKey())       // default : ROLE_USER
                                    .tagAllowYn("Y")
                                    .pushViewYn("Y")
                                    .joinDttm(LocalDateTime.now())
                                    .useYn("Y")
                                    .build();
        
        memberInfoRepository.save(memberInfo);

        // 3. 회원정보 History save
        MemberInfoHistory historyEntity = MemberInfoHistory.builder()
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

        // 1. 아이디 검증
        if(StringUtils.isNotEmpty(member.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"아이디를 입력해주세요.");

        // 2. 패스워드 검증 & 암호화 처리
        if(StringUtils.isNotEmpty(member.getPassword())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"패스워드를 입력해주세요.");
        else member.setPassword(encoder.encode(member.getPassword()));

        // 3. 전화번호 검증
        if(StringUtils.isNotEmpty(member.getTel())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"전화번호를 입력해주세요.");
        else {
            member.setTel(member.getTel().replaceAll("-", ""));
            if(member.getTel().length() != 11) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"입력한 전화번호를 확인해주세요.");
        }

        // 4. 성별 검증 & 닉네임검증
        if(StringUtils.isNotEmpty(member.getSex())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "성별을 입력해주세요.");
        if(StringUtils.isNotEmpty(member.getNickname())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "닉네임을 입력해주세요.");
        /* TODO: 닉네임 검증(영어 + 특수문자? 기준 정해서 추가.) */

        // 5. 닉네임 중복여부 체크
        Optional<MemberInfo> nicknameCheck = memberInfoRepository.findByNickname(member.getNickname());
        if(nicknameCheck.isPresent()) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), "이미 사용중인 닉네임입니다.");

        // 6. 아이디 중복여부 체크
        Optional<MemberInfo> entity = memberInfoRepository.findById(member.getId());

        if(entity.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"이미 등록된 사용자입니다.");

        log.info("============== 회원가입 검증 종료 ==============");
    }

    /* 로그인 */
    public Token login(MemberInfoPVO pvo, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException {

        // 1. 파라미터 검증
        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"아이디를 입력해주세요.");
        if(StringUtils.isNotEmpty(pvo.getPassword())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(),"패스워드를 입력해주세요.");

        // 2. 사용자 존재 여부 체크
        MemberInfo loginEntity = memberInfoRepository.findById(pvo.getId())
                .orElseThrow(() -> new IllegalAccessException("가입하지 않은 사용자입니다."));

        // 3. 사용여부 체크
        if(!"Y".equals(loginEntity.getUseYn())) throw new IllegalAccessException("사용하지 않는 계정입니다.\n로그인 할 수 없습니다.");

        // 4. 패스워드 일치 여부 체크
        String password = loginEntity.getPassword();
        if(!encoder.matches(pvo.getPassword(), password)) {
            memberInfoRepository.save(loginEntity.loginFalse());
            throw new IllegalAccessException("비밀번호가 틀렸습니다.");
        }

        // 5. 패스워드 틀린횟수 체크
        if(loginEntity.getWrongPasswordNumber() >= 5) throw new IllegalAccessException("패스워드 5회 이상 틀리셨습니다.\n고객센터에 문의바랍니다.");

        // 6. 로그인 성공 시, 정보 Update 및 Token 생성
        memberInfoRepository.save(loginEntity.loginSuccess());

        // 7. 로그인 이력 생성
        this.loginHistorySave(loginEntity.getId(), request);

        // 6-1. Jwt Token 생성
        Token token = this.tokenGenerate(loginEntity.getId(), loginEntity.getRole());

        // 6-2. Refresh Token Http Cookie 저장
        CommonUtils.setHttpCookie(new CookieInfo(token.getRefreshToken()), response);

        return token;
    }

    /*
     * 로그인 후, 토큰 생성
     * */
    public Token tokenGenerate(String id, String role) {
        List<String> roles = new ArrayList<>();
        roles.add(role);

        return jwtTokenProvider.generateJwtToken(id, roles);
    }

    /*
     * 로그아웃
     * */
    public void logout(HttpServletResponse response) {
        CommonUtils.setHttpCookie(new CookieInfo(""), response);
    }

    /*
    * Token 재발급
    * */
    public Token tokenReIssue(String refreshToken) {
        log.info(refreshToken);
        // 1. Refresh Token 유효성 체크
        if(!jwtTokenProvider.validDateToken(refreshToken)) throw new CustomException(Response.EXPIRATION_ERROR.getCode(), Response.EXPIRATION_ERROR.getMessage());

        String id = jwtTokenProvider.parseClaims(refreshToken).getSubject();

        MemberInfo memberInfo = memberInfoRepository.findById(id).get();
        Token token = this.tokenGenerate(memberInfo.getId(), memberInfo.getRole());

        return token;
    }
    
    /*
    * 로그인 이력 생성
    * */
    public LoginHistory loginHistorySave(String id, HttpServletRequest request) {
        String clientIp = request.getHeader("X-FORWARDED-FOR");

        LoginHistory loginHistory = LoginHistory.builder()
                .id(id)
                .loginDttm(LocalDateTime.now())
                .loginIp(clientIp != null ? clientIp : request.getRemoteAddr())
                .loginUri(request.getRequestURI())
                .build();
        return loginHistoryRepository.save(loginHistory);
    }

    /*
    * 사용자 정보 수정
    * */
    public MemberInfo memberInfoUpdate(MemberInfoPVO pvo) throws IllegalAccessException {

        Optional<MemberInfo> memberInfoOpt = memberInfoRepository.findByIdAndUseYn(pvo.getId(), "Y");

        if(memberInfoOpt.isPresent()) {
            MemberInfo memberInfo = memberInfoOpt.get();

            // 1. 비밀번호 검증
            if(!encoder.matches(pvo.getPassword(), memberInfo.getPassword())) throw new IllegalAccessException("비밀번호가 틀렸습니다.");

            // 2. 변경할 비밀번호 체크 & 암호화
            if(!StringUtils.isNotEmpty(pvo.getNewPassword())){
                if(!pvo.getNewPassword().equals(pvo.getNewPasswordCheck())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "변경할 비밀번호가 동일하지 않습니다.");
                if(encoder.matches(pvo.getNewPassword(), memberInfo.getPassword())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "변경할 비밀번호가 기존과 동일합니다.");
                pvo.setNewPassword(encoder.encode(pvo.getNewPassword()));
            }

            // 3. 권한변경 시, 관리자 여부 체크
            if(!StringUtils.isNotEmpty(pvo.getRole()) && !"ROLE_ADMIN".equals(memberInfo.getRole())) throw new CustomException(Response.FORBIDDEN_ERROR.getCode(), Response.FORBIDDEN_ERROR.getMessage());

            // 4. 닉네임 중복 체크
            if(!StringUtils.isNotEmpty(pvo.getNickname()) && !memberInfo.getNickname().equals(pvo.getNickname())) {
                Optional<MemberInfo> nicknameCheck = memberInfoRepository.findByNickname(pvo.getNickname());
                if(nicknameCheck.isPresent()) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), "이미 사용중인 닉네임입니다.");
            }

            memberInfo.updateMemberInfo(pvo);
            memberInfoRepository.save(memberInfo);

            return memberInfo;
        }
        else {
            throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "계정이 존재하지 않습니다.");
        }
    }

    /*
    * 회원정보 전체 조회
    * */
    public List<MemberInfo> selectMemberInfoList() {
        return memberInfoRepository.selectMemberInfoList();
    }

    /*
    * 회원정보 조회
    * */
    public MemberInfo selectMemberInfo(MemberInfoPVO pvo) {
        Optional<MemberInfo> memberInfoOpt = memberInfoRepository.findByIdAndUseYn(pvo.getId(), "Y");
        if(memberInfoOpt.isPresent()) {
            return memberInfoOpt.get();
        } else {
            throw new CustomException(Response.UNAUTHORIZED_ERROR.getCode(), Response.UNAUTHORIZED_ERROR.getMessage());
        }
    }

    /*
    * 회원정보 조회
    *  */
    public List<MemberInfoRVO> selectMemberInfoSearchList(SearchPVO pvo) {
        return memberInfoDslRepository.selectSearchMemberInfoList(pvo);
    }

}
