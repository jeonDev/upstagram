package com.api.upstagram.domain.Story;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryWatchingRepository extends JpaRepository<StoryWatchingEntity, Long> {
    Optional<StoryWatchingEntity> findByStoryNoAndId(Long storyNo, String id);
}
