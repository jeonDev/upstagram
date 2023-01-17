package com.api.upstagram.vo.Feed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedTagRVO {
    private Long feedTagNo;
    private Long feedNo;
    private String id;
    private String oauthNo;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
}
