package com.api.upstagram.domain.Story;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryWatchingRepository extends JpaRepository<StoryWatchingEntity, Long> {
    
}
