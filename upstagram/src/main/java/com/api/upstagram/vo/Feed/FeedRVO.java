package com.api.upstagram.vo.Feed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedRVO {
    private Long feedNo;
    private String title;
    private String hashtag;
    private String useYn;

    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;

    private int feedHeartCnt;
    private int feedCommentCnt;

    private String feedFileNames;
    private String feedExts;
}
