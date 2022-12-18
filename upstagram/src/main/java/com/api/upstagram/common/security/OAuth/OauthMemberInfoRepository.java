package com.api.upstagram.common.security.OAuth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.domain.OauthMemberInfo.Entity.OauthMemberInfo;

public interface OauthMemberInfoRepository extends JpaRepository<OauthMemberInfo, Long> {
    Optional<OauthMemberInfoRepository> findByEmail(String email);
}
