package com.api.upstagram.vo.Story;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
public class StoryWatchingRVO {
    private Long storyWatchingNo;
    private Long storyNo;
    private String id;
    private LocalDateTime firstWatchingDttm;
    private LocalDateTime lastWatchingDttm;

    @Builder
    public StoryWatchingRVO(Long storyWatchingNo, Long storyNo, String id, LocalDateTime firstWatchingDttm,
            LocalDateTime lastWatchingDttm) {
        this.storyWatchingNo = storyWatchingNo;
        this.storyNo = storyNo;
        this.id = id;
        this.firstWatchingDttm = firstWatchingDttm;
        this.lastWatchingDttm = lastWatchingDttm;
    }
    
}
