package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.memberInfo.MemberInfoHistoryEntity;

public interface MemberInfoHistoryRepository extends JpaRepository<MemberInfoHistoryEntity, String> {

}
