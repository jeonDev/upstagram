package com.api.upstagram.domain.Feed;

import com.api.upstagram.vo.Feed.FeedRVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query(nativeQuery = true,
        value = "SELECT f.FEED_NO AS feedNo" +
                "     , f.TITLE AS title" +
                "     , f.HASHTAG AS hashtag" +
                "     , f.USE_YN AS useYn" +
                "     , mi.ID AS id" +
                "     , mi.NAME AS name" +
                "     , mi.NICKNAME AS nickname" +
                "     , mi.SEX AS sex" +
                "     , mi.TEL AS tel" +
                "  FROM FEED f" +
                "  JOIN (" +
                "       SELECT mi.*" +
                "         FROM FOLLOW_USER fu" +
                "         JOIN MEMBER_INFO mi ON fu.FOLLOW_ID = mi.ID AND mi.USE_YN = 'Y'" +
                "        WHERE fu.ID = :id" +
                "       ) mi ON f.ID = mi.ID" +
                " WHERE f.USE_YN = 'Y'" +
                " ORDER BY f.REG_DTTM DESC")
    List<FeedRVO> selectFeedList(@Param("id") String id);
}
