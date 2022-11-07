package com.api.upstagram.vo.Feed;

public interface FeedCommentInterface {
    Long getFeedCommentNo();
    Long getFeedNo();
    String getId();
    String getName();
    String getNickname();
    String getSex();
    String getTel();
    String getOauthNo();
    String getContent();
    String getUseYn();
    String getTopFix();
    int getHeartCnt();
}
