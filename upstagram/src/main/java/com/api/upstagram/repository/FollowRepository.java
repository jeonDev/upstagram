package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.Follow.FollowUserEntity;

public interface FollowRepository extends JpaRepository<FollowUserEntity, Long> {
    
}
