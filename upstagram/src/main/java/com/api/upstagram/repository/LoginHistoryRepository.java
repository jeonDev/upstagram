package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.LoginHistory.LoginHistoryEntity;

public interface LoginHistoryRepository extends JpaRepository<LoginHistoryEntity, String> {
    
}
