package com.api.upstagram.domain.FollowUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowUserRepository extends JpaRepository<FollowUserEntity, Long> {
    List<FollowUserEntity> findById(String id);
    Optional<FollowUserEntity> findByFollowNo(Long followNo);
}
