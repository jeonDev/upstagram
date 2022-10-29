package com.api.upstagram.domain.Story;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryWatchingRepository extends JpaRepository<StoryWatching, Long> {
    Optional<StoryWatching> findByStoryNoAndId(Long storyNo, String id);
}
