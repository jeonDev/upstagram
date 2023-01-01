package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.FeedHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedHashtagRepository extends JpaRepository<FeedHashtag, Long> {
}
