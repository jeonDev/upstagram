package com.api.upstagram.domain.Story;

import javax.persistence.*;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Story.StoryWatchingRVO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STORY_WATCHING")
@Entity
public class StoryWatching extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyWatchingNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORY_NO")
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    @Builder
    public StoryWatching(Long storyWatchingNo, Story story, MemberInfo member) {
        this.storyWatchingNo = storyWatchingNo;
        this.story = story;
        this.member = member;
    }

    public StoryWatchingRVO storyWatchingToRVO() {
        return StoryWatchingRVO.builder()
                .storyWatchingNo(this.storyWatchingNo)
                .storyNo(this.story.getStoryNo())
                .firstWatchingDttm(this.getRegDttm())
                .lastWatchingDttm(this.getLastDttm())
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .sex(this.member.getSex())
                .tel(this.member.getTel())
                .oauthNo(this.member.getOauthNo())
                .build();
    }
}
