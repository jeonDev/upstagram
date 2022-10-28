package com.api.upstagram.vo.Story;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryReactionPVO {
    private Long storyNo;
    private String id;
    private String storyLoveYn;
    private LocalDateTime storyViewDate;
}
