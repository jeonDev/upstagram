package com.api.upstagram.domain.memberInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, String> {
    Optional<MemberInfoEntity> findByIdAndUseYn(String id, String useYn);
    Optional<MemberInfoEntity> findByNickname(String nickname);
}
