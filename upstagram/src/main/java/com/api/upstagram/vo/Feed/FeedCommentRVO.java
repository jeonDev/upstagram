package com.api.upstagram.vo.Feed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedCommentRVO {
    private Long feedCommentNo;
    private Long feedNo;
    private String id;
    private String content;
    private String useYn;
    private String topFix;

    private Long feedCommentHeartNo;
    private String commentHeartId;
}
