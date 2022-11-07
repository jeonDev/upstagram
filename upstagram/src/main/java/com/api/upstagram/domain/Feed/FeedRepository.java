package com.api.upstagram.domain.Feed;

import com.api.upstagram.vo.Feed.FeedInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

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
            "     , GROUP_CONCAT(ff.fileName, ' ')  AS fileNames " +
            "  FROM Feed f" +
            "  JOIN FollowUser fu ON fu.followMember.id = f.member.id" +
            "   AND fu.idMember.id = :id" +
            "  JOIN MemberInfo m ON f.member.id = m.id" +
            "   AND m.useYn = 'Y'" +
            "  LEFT JOIN FeedHeart fh ON fh.feed.feedNo = f.feedNo" +
            "  JOIN FeedFile ff ON ff.feed.feedNo = f.feedNo" +
            " WHERE f.useYn = 'Y'" +
            " GROUP BY f.feedNo" +
            " ORDER BY MAX(f.regDttm) DESC")
    List<FeedInterface> selectFeedList(@Param("id") String id);
}
