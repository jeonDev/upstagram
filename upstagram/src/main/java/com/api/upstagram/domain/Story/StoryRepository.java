package com.api.upstagram.domain.Story;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.upstagram.vo.Story.StoryInterface;

public interface StoryRepository extends JpaRepository<Story, Long> {

    /* 나의 Story 조회 */
    @Query(nativeQuery = true,
        value = "SELECT s.STORY_NO AS storyNo" +
            "         , s.STORY_FILE_NAME AS storyFileName" +
            "         , s.STORY_TIME AS storyTime" +
            "         , s.SHOW_YN AS showYn" +
            "         , s.KEEP_YN AS keepYn" +
            "         , :id AS id" +
            "         , m.ID AS followId" +
            "         , m.NAME AS followName" +
            "         , m.NICKNAME AS followNickname" +
            "         , m.SEX AS followSex" +
            "         , m.TEL AS followTel" +
            "         , m.OAUTH_NO AS followOauthNo" +
            "     FROM STORY s" +
            "     JOIN MEMBER_INFO m ON s.ID = m.ID" +
            "    WHERE s.SHOW_YN = 'Y'" +
            "      AND (s.KEEP_YN = 'Y'" +
            "       OR s.REG_DTTM > DATE_ADD(NOW(), INTERVAL - 1 DAY))" +
            "      AND s.ID = :id" +
            "    ORDER BY s.REG_DTTM DESC")
    public List<StoryInterface> findByMyStoryList(@Param("id") String id);

    /* Follow의 Story 조회 */
    @Query(nativeQuery = true,
        value = "SELECT s.STORY_NO AS storyNo" +
            "         , s.STORY_FILE_NAME AS storyFileName" +
            "         , s.STORY_TIME AS storyTime" +
            "         , s.SHOW_YN AS showYn" +
            "         , s.KEEP_YN AS keepYn" +
            "         , :id AS id" +
            "         , m.ID AS followId" +
            "         , m.NAME AS followName" +
            "         , m.NICKNAME AS followNickname" +
            "         , m.SEX AS followSex" +
            "         , m.TEL AS followTel" +
            "         , m.OAUTH_NO AS followOauthNo" +
            "     FROM STORY s" +
            "     JOIN MEMBER_INFO m ON s.ID = m.ID" +
            "    WHERE s.SHOW_YN = 'Y'" +
            "      AND s.REG_DTTM > DATE_ADD(NOW(), INTERVAL - 1 DAY)" +
            "      AND s.ID IN ( " +
            "                   SELECT f.FOLLOW_ID" +
            "                     FROM FOLLOW_USER f" +
            "                     JOIN MEMBER_INFO fm ON f.FOLLOW_ID = fm.ID" +
            "                    WHERE f.ID = :id" +
            "                      AND fm.USE_YN = 'Y'" +
            "                   )" +
            "    ORDER BY s.REG_DTTM DESC")
    public List<StoryInterface> findByFollowStoryList(@Param("id") String id);
}
