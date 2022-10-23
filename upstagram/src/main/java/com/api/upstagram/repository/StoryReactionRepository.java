package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.Story.StoryReactionEntity;

public interface StoryReactionRepository extends JpaRepository<StoryReactionEntity, Long>{
    
}
