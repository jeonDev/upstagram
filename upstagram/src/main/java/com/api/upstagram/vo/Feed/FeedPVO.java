package com.api.upstagram.vo.Feed;

import lombok.Data;

import java.util.List;

@Data
public class FeedPVO {
    private String title;
    private String hashtag;
    private String useYn;
    private String id;

    private List<String> tagId;
}
