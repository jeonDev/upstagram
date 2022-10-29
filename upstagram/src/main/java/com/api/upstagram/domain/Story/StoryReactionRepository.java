package com.api.upstagram.domain.Story;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryReactionRepository extends JpaRepository<StoryReaction, Long>{
    Optional<StoryReaction> findByStoryAndId(Story story, String id);
}
