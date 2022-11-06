package com.api.upstagram.vo.Story;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoryWatchingRVO {
    private Long storyWatchingNo;
    private Long storyNo;
    private LocalDateTime firstWatchingDttm;
    private LocalDateTime lastWatchingDttm;

    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
}
