package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.Feed;
import com.api.upstagram.domain.Feed.Entity.FeedHeart;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedHeartRepository extends JpaRepository<FeedHeart, Long> {
    Optional<FeedHeart> findByFeedAndMember(Feed feed, MemberInfo member);

    /* Feed 좋아요 조회*/
    @Query(value =
            "SELECT fh, m" +
            "  FROM FeedHeart fh" +
            "  JOIN MemberInfo m ON fh.member = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE fh.feed.feedNo = :feedNo" +
            " ORDER BY fh.regDttm DESC")
    List<FeedHeart> selectFeedHeartList(@Param("feedNo") Long feedNo);

    /* Feed 좋아요 갯수 조회*/
    @Query(value =
            "SELECT COUNT(fh)" +
            "  FROM FeedHeart fh" +
            "  JOIN MemberInfo m ON fh.member = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE fh.feed.feedNo = :feedNo")
    int selectFeedHeartCnt(@Param("feedNo") Long feedNo);
}
