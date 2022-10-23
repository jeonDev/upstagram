package com.api.upstagram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.memberInfo.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, String> {
    Optional<MemberInfoEntity> findByIdAndUseYn(String id, String useYn);
    Optional<MemberInfoEntity> findByNickname(String nickname);
}
