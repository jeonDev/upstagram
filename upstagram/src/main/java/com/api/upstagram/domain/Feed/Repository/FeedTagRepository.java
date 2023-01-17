package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.FeedTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedTagRepository extends JpaRepository<FeedTag, Long> {

    @Query(value =
            "SELECT ft" +
            "     , mi" +
            "  FROM FeedTag ft" +
            "  JOIN MemberInfo mi ON ft.member.id = mi.id" +
            "   AND mi.useYn = 'Y'" +
            " WHERE ft.feed.feedNo = :feedNo"
    )
    List<FeedTag> selectFeedTagList(@Param("feedNo") Long feedNo);
}
