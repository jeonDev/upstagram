package com.api.upstagram.vo.Feed;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class FeedHashtagRVO {

    private String hashtagNo;
    private String hashtag;
    private int hashtagCnt;

    @QueryProjection
    public FeedHashtagRVO(String hashtagNo, String hashtag, int hashtagCnt) {
        this.hashtagNo = hashtagNo;
        this.hashtag = hashtag;
        this.hashtagCnt = hashtagCnt;
    }
    @QueryProjection
    public FeedHashtagRVO(String hashtag, int hashtagCnt) {
        this.hashtag = hashtag;
        this.hashtagCnt = hashtagCnt;
    }
}
