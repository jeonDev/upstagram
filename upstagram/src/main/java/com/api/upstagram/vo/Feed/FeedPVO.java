package com.api.upstagram.vo.Feed;

import lombok.Data;

import java.util.List;

@Data
public class FeedPVO {
    private String title;
    private String useYn;
    private String id;
    private String feedKeepYn;

    private List<String> hashtag;
    private List<String> tagId;
}
