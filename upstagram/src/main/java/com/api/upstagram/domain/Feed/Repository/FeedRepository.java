package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

}
