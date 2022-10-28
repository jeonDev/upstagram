package com.api.upstagram.domain.Story;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryReactionRepository extends JpaRepository<StoryReactionEntity, Long>{
    Optional<StoryReactionEntity> findByStoryAndId(StoryEntity story, String id);
}
