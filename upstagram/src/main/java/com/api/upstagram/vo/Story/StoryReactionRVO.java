package com.api.upstagram.vo.Story;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryReactionRVO {
    private Long reactionNo;
    private Long storyNo;
    private String id;
    private String storyLoveYn;
    private LocalDateTime storyViewDate;

    @Builder
    public StoryReactionRVO(Long reactionNo, Long storyNo, String id, String storyLoveYn, LocalDateTime storyViewDate) {
        this.reactionNo = reactionNo;
        this.storyNo = storyNo;
        this.id = id;
        this.storyLoveYn = storyLoveYn;
        this.storyViewDate = storyViewDate;
    }

}