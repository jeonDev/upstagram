package com.api.upstagram.domain.Story;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StoryRepository extends JpaRepository<Story, Long> {

    /* 나의 Story 조회 */
    @Query(value =
            "SELECT s, m" +
            "  FROM Story s" +
            "  JOIN MemberInfo m ON s.member = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE s.showYn = 'Y'" +
            "   AND (" +
            "       s.keepYn = 'Y'" +
            "    OR s.regDttm > :dttm" +
            "       )" +
            "   AND s.member.id = :id" +
            " ORDER BY s.regDttm DESC")
    List<Story> selectMyStoryList(@Param("id") String id, @Param("dttm") LocalDateTime dttm);

    /* Follow의 Story 조회 */
    @Query(value =
            "SELECT s, m" +
            "  FROM Story s" +
            "  JOIN MemberInfo m ON s.member = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE s.showYn = 'Y'" +
            "   AND s.regDttm > :dttm" +
            "   AND s.member.id IN (" +
            "                       SELECT f.followMember.id" +
            "                         FROM FollowUser f" +
            "                         JOIN MemberInfo fm ON f.followMember = fm.id" +
            "                        WHERE f.idMember.id = :id" +
            "                          AND fm.useYn = 'Y'" +
            "                       )" +
            " ORDER BY s.regDttm DESC")
    List<Story> selectFollowStoryList(@Param("id") String id, @Param("dttm") LocalDateTime dttm);
}
