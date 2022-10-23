package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.Story.StoryEntity;

public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
    
}
