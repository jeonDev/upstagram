package com.api.upstagram.domain.Feed;

import com.api.upstagram.vo.Feed.FeedRVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    /* Feed 조회 */
    @Query(value =
            "SELECT f, m, ff" +
            "  FROM Feed f" +
            "  JOIN MemberInfo m ON f.member = m.id" +
            "   AND m.useYn = 'Y'" +
            "  JOIN FollowUser fu ON m.id = fu.followMember" +
            "   AND fu.idMember.id = :id" +
            " WHERE f.useYn = 'Y'")
    List<Feed> selectFeedList(@Param("id") String id);
}
