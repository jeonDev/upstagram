package com.api.upstagram.vo.Feed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedCommentRVO {
    private Long feedCommentNo;
    private Long feedNo;

    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;

    private String content;
    private String useYn;
    private String topFix;
    private int heartCnt;

    private Long feedCommentHeartNo;
    private String commentHeartId;

    private int feedCommentCnt;
}
