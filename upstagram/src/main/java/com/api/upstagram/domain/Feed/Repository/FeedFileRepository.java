package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.FeedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedFileRepository extends JpaRepository<FeedFile, Long> {
}
