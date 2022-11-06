package com.api.upstagram.domain.Story;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Story.StoryReactionRVO;
import lombok.Builder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STORY_REACTION")
@Entity
public class StoryReaction extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORY_NO")
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;
    private String storyLoveYn;
    private LocalDateTime storyViewDate;


    @Builder
    public StoryReaction(Long reactionNo, Story story, MemberInfo member, String storyLoveYn, LocalDateTime storyViewDate) {
        this.reactionNo = reactionNo;
        this.story = story;
        this.member = member;
        this.storyLoveYn = storyLoveYn;
        this.storyViewDate = storyViewDate;
    }

    public StoryReactionRVO storyReactionToRVO() {
        return StoryReactionRVO.builder()
                .reactionNo(this.reactionNo)
                .storyNo(this.story.getStoryNo())
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .sex(this.member.getSex())
                .tel(this.member.getTel())
                .oauthNo(this.member.getOauthNo())
                .storyLoveYn(this.storyLoveYn)
                .storyViewDate(this.storyViewDate)
                .build();
    }

    public StoryReaction updateStoryReaction(String storyLoveYn) {
        this.storyLoveYn = storyLoveYn;
        this.storyViewDate = LocalDateTime.now();
        return this;
    }
}
