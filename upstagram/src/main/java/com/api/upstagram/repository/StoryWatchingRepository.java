package com.api.upstagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.upstagram.entity.Story.StoryWatchingEntity;

public interface StoryWatchingRepository extends JpaRepository<StoryWatchingEntity, Long> {
    
}
