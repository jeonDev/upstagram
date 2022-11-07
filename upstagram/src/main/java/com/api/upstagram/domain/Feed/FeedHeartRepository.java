package com.api.upstagram.domain.Feed;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedHeartRepository extends JpaRepository<FeedHeart, Long> {
    Optional<FeedHeart> findByFeedAndMember(Feed feed, MemberInfo member);

    /* Feed 좋아요 조회*/
    @Query(value =
            "SELECT fh, m" +
            "  FROM FeedHeart fh" +
            "  JOIN MemberInfo m ON fh.member = m.id" +
            " WHERE fh.feed.feedNo = :feedNo" +
            " ORDER BY fh.regDttm DESC")
    List<FeedHeart> selectFeedHeartList(Long feedNo);
}
