package com.api.upstagram.vo.Story;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class StoryWatchingPVO {
    private Long storyNo;
    private Long followNo;
    private String id;
}
