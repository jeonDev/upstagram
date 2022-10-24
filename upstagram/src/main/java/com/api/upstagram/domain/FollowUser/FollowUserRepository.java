package com.api.upstagram.domain.FollowUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.domain.memberInfo.MemberInfoEntity;

public interface FollowUserRepository extends JpaRepository<FollowUserEntity, Long> {
    List<FollowUserEntity> findByIdMember(MemberInfoEntity idMember);
    List<FollowUserEntity> findByFollowMember(MemberInfoEntity followMember);
    Optional<FollowUserEntity> findByFollowNo(Long followNo);
}
