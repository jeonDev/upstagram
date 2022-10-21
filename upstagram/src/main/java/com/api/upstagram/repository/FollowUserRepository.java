package com.api.upstagram.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.FollowUser.FollowUserEntity;

public interface FollowUserRepository extends JpaRepository<FollowUserEntity, Long> {
    List<FollowUserEntity> findById(String id);
    Optional<FollowUserEntity> findByFollowNo(Long followNo);
}
