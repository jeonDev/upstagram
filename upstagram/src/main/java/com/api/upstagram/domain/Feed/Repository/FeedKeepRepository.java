package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.Feed;
import com.api.upstagram.domain.Feed.Entity.FeedKeep;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedKeepRepository extends JpaRepository<FeedKeep, Long> {
    Optional<FeedKeep> findByFeedAndMember(Feed feed, MemberInfo member);
}
