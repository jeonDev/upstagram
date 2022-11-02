package com.api.upstagram.domain.Story;

import java.time.LocalDateTime;

import javax.persistence.*;

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

    private String id;
    private String storyLoveYn;
    private LocalDateTime storyViewDate;

    @Builder
    public StoryReaction(Long reactionNo, Story story, String id, String storyLoveYn, LocalDateTime storyViewDate) {
        this.reactionNo = reactionNo;
        this.story = story;
        this.id = id;
        this.storyLoveYn = storyLoveYn;
        this.storyViewDate = storyViewDate;
    }
    
    public StoryReaction updateStoryReaction(String storyLoveYn) {
        this.storyLoveYn = storyLoveYn;
        this.storyViewDate = LocalDateTime.now();
        return this;
    }
}