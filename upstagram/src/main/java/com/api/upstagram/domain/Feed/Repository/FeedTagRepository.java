package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.FeedTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedTagRepository extends JpaRepository<FeedTag, Long> {
}
