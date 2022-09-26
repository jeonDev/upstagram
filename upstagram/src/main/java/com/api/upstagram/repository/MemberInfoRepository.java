package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.upstagram.entity.memberInfo.MemberInfoEntity;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, String> {
    MemberInfoEntity findByIdAndUseYn(String id, String useYn);
}
