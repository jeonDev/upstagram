package com.api.upstagram.vo.Feed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedKeepRVO {

    private Long feedKeepNo;
    private Long feedNo;
    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
}
