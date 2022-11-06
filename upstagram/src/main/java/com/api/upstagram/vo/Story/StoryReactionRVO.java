package com.api.upstagram.vo.Story;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StoryReactionRVO {
    private Long reactionNo;
    private Long storyNo;
    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
    private String storyLoveYn;
    private LocalDateTime storyViewDate;

}