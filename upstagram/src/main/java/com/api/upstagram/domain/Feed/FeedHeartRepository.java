package com.api.upstagram.domain.Feed;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedHeartRepository extends JpaRepository<FeedHeart, Long> {
    Optional<FeedHeart> findByFeedAndMember(Feed feed, MemberInfo member);
}