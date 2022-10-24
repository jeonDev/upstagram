package com.api.upstagram.domain.Story;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryReactionRepository extends JpaRepository<StoryReactionEntity, Long>{
    
}
