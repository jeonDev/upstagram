package com.api.upstagram.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.upstagram.common.vo.User;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.domain.MemberInfo.Repository.MemberInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.info("로그인 인증 시도! => loadUserByUsername");

        MemberInfo memberInfoEntity = memberInfoRepository.findByIdAndUseYn(id, "Y")
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        List<String> roles = new ArrayList<>();
        roles.add(memberInfoEntity.getRole());

        User user = User.builder()
                        .id(memberInfoEntity.getId())
                        .name(memberInfoEntity.getName())
                        .tel(memberInfoEntity.getTel())
                        .sex(memberInfoEntity.getSex())
                        .roles(roles)
                        .build();
        return user;
    }
    
}
