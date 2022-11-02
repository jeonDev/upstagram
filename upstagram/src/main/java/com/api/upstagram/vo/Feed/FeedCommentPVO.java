package com.api.upstagram.vo.Feed;

import lombok.Data;

@Data
public class FeedCommentPVO {
    private Long feedNo;
    private String id;
    private String content;
    private String topFix;
}
