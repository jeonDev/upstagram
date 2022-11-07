package com.api.upstagram.domain.Feed;

import com.api.upstagram.vo.Feed.FeedCommentInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {

    /* Feed Comment 조회 */
    @Query(value =
            "SELECT fc.feedCommentNo AS feedCommentNo" +
            "     , MAX(fc.feed.feedNo) AS feedNo" +
            "     , MAX(m.id) AS id" +
            "     , MAX(m.name) AS name" +
            "     , MAX(m.nickname) AS nickname" +
            "     , MAX(m.sex) AS sex" +
            "     , MAX(m.tel) AS tel" +
            "     , MAX(m.oauthNo) AS oauthNo" +
            "     , MAX(fc.content) AS content" +
            "     , MAX(fc.useYn) AS useYn" +
            "     , MAX(fc.topFix) AS topFix" +
            "     , COUNT(fch.feedCommentHeartNo) AS heartCnt" +
            "  FROM FeedComment fc" +
            "  LEFT JOIN FeedCommentHeart fch ON fch.feedComment.feedCommentNo = fc.feedCommentNo" +
            "  JOIN MemberInfo m ON fc.member.id = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE fc.feed.feedNo = :feedNo" +
            "   AND fc.useYn = 'Y'" +
            " GROUP BY fc.feedCommentNo")
    List<FeedCommentInterface> selectFeedCommentList(@Param("feedNo") Long feedNo);
}
