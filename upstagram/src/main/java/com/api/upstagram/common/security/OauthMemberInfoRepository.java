package com.api.upstagram.common.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.common.vo.OauthMemberInfoEntity;

public interface OauthMemberInfoRepository extends JpaRepository<OauthMemberInfoEntity, Long> {
    Optional<OauthMemberInfoRepository> findByEmail(String email);

    OauthMemberInfoEntity save(OauthMemberInfoEntity user);
    
}
