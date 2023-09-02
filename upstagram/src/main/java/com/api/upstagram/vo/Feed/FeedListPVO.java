package com.api.upstagram.vo.Feed;

import lombok.Data;

@Data
public class FeedListPVO {
    private String id;                  // 사용자 ID
    private String writerId;            // 작성자 ID
    private String tagId;               // 태그 ID
    private String hashtag;             // 해시태그
    private String feedKeepYn;          // 보관 여부
    private String feedHeartYn;         // 좋아요 여부
    // 1 : 팔로우 피드, 2 : Keep Feed, 3 : User Feed, 4 : Tag Feed, 5 : Hashtag Feed, 6 : Heart Feed
    private String feedDivisionCode;    // 구분
}
