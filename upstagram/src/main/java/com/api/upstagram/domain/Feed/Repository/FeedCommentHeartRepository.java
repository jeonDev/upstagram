package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.FeedComment;
import com.api.upstagram.domain.Feed.Entity.FeedCommentHeart;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedCommentHeartRepository extends JpaRepository<FeedCommentHeart, Long> {
    Optional<FeedCommentHeart> findByFeedCommentAndMember(FeedComment feedComment, MemberInfo member);
}
