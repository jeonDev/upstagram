package com.api.upstagram.vo.Story;

import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoryRVO {
    private Long storyNo;
    private String storyFileName;
    private String storyTime;
    private String showYn;
    private String keepYn;
    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
}
