package com.api.upstagram.domain.Story;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoryReactionRepository extends JpaRepository<StoryReaction, Long>{
    Optional<StoryReaction> findByStoryAndMember(Story story, MemberInfo member);

    /* 스토리 반응 조회 */
    @Query(value =
            "SELECT sr, s, m" +
            "  FROM StoryReaction sr" +
            "  JOIN Story s ON sr.story = s.storyNo" +
            "  JOIN MemberInfo m ON sr.member = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE sr.story.storyNo = :storyNo")
    List<StoryReaction> selectStoryReactionList(@Param("storyNo") Long storyNo);
}
