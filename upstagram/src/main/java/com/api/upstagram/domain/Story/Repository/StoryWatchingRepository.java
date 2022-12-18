package com.api.upstagram.domain.Story.Repository;

import java.util.List;
import java.util.Optional;

import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.domain.Story.Entity.Story;
import com.api.upstagram.domain.Story.Entity.StoryWatching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryWatchingRepository extends JpaRepository<StoryWatching, Long> {
    Optional<StoryWatching> findByStoryAndMember(Story story, MemberInfo member);

    @Query(value =
            "SELECT s" +
            "  FROM StoryWatching s" +
            "  JOIN MemberInfo m ON s.member = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE s.story.storyNo = :storyNo" +
            " ORDER BY s.regDttm DESC"
    )
    List<StoryWatching> selectWatchingList(@Param("storyNo") Long storyNo);
}
