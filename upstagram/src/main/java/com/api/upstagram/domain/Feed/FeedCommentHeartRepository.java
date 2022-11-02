package com.api.upstagram.domain.Feed;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedCommentHeartRepository extends JpaRepository<FeedCommentHeart, Long> {
    Optional<FeedCommentHeart> findByFeedCommentAndMember(FeedComment feedComment, MemberInfo member);
}
