package com.api.upstagram.domain.MemberInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, String> {
    Optional<MemberInfo> findByIdAndUseYn(String id, String useYn);
    Optional<MemberInfo> findByNickname(String nickname);
}
