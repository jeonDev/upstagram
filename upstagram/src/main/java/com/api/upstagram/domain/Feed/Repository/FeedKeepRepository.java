package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.Feed;
import com.api.upstagram.domain.Feed.Entity.FeedKeep;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.vo.Feed.FeedInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedKeepRepository extends JpaRepository<FeedKeep, Long> {
    Optional<FeedKeep> findByFeedAndMember(Feed feed, MemberInfo member);

    /* Feed Keep 내역 조회 */
    @Query(value =
            "SELECT f.feedNo AS feedNo" +
            "     , MAX(f.title) AS title" +
            "     , MAX(f.hashtag) AS hashtag" +
            "     , MAX(f.useYn) AS useYn" +
            "     , MAX(m.id) AS id" +
            "     , MAX(m.name) AS name" +
            "     , MAX(m.nickname) AS nickname" +
            "     , MAX(m.sex) AS sex" +
            "     , MAX(m.tel) AS tel" +
            "     , MAX(m.oauthNo) AS oauthNo" +
            "     , COUNT(fh.feedHeartNo) AS feedHeartCnt" +
            "     , COUNT(fc.feedCommentNo) AS feedCommentCnt" +
            "     , GROUP_CONCAT(ff.fileName, ' ')  AS fileNames " +
            "  FROM Feed f" +
            "  JOIN FeedKeep keep ON keep.feed.feedNo = f.feedNo" +
            "   AND keep.member.id = :id" +
            "  JOIN MemberInfo m ON f.member.id = m.id" +
            "   AND m.useYn = 'Y'" +
            "  LEFT JOIN FeedHeart fh ON fh.feed.feedNo = f.feedNo" +
            "  JOIN FeedFile ff ON ff.feed.feedNo = f.feedNo" +
            "  LEFT JOIN FeedComment fc ON fc.feed.feedNo = f.feedNo" +
//            "  LEFT JOIN FeedTag ft ON ft.feed.feedNo = f.feedNo" +
            " WHERE f.useYn = 'Y'" +
            " GROUP BY f.feedNo" +
            " ORDER BY MAX(f.regDttm) DESC")
    List<FeedInterface> selectFeedKeepList(@Param("id") String id);
}
