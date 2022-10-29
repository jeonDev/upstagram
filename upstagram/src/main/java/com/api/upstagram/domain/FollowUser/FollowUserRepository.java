package com.api.upstagram.domain.FollowUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.domain.memberInfo.MemberInfo;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
    List<FollowUser> findByIdMember(MemberInfo idMember);
    List<FollowUser> findByFollowMember(MemberInfo followMember);
    Optional<FollowUser> findByFollowNo(Long followNo);
}
