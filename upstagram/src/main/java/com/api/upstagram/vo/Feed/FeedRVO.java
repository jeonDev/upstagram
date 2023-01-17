package com.api.upstagram.vo.Feed;

import com.querydsl.core.annotations.QueryProjection;
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
    private Long feedKeepNo;
    private Long feedHeartNo;
    private String feedTagIds;

    @QueryProjection
    public FeedRVO(Long feedNo, String title, String hashtag, String useYn, String id, String name, String nickname, String sex, String tel, String oauthNo, int feedHeartCnt, int feedCommentCnt, String feedFileNames, String feedExts, Long feedKeepNo, Long feedHeartNo, String feedTagIds) {
        this.feedNo = feedNo;
        this.title = title;
        this.hashtag = hashtag;
        this.useYn = useYn;
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.tel = tel;
        this.oauthNo = oauthNo;
        this.feedHeartCnt = feedHeartCnt;
        this.feedCommentCnt = feedCommentCnt;
        this.feedFileNames = feedFileNames;
        this.feedExts = feedExts;
        this.feedKeepNo = feedKeepNo;
        this.feedHeartNo = feedHeartNo;
        this.feedTagIds = feedTagIds;
    }
}
