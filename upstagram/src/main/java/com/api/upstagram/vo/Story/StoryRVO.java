package com.api.upstagram.vo.Story;

import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoryRVO {
    private Long storyNo;
    private String id;
    private String storyFileName;
    private String storyTime;
    private String showYn;
    private String keepYn;
    private MemberInfoRVO member;

    
    public StoryRVO(Long storyNo, String id, String storyFileName, String storyTime, String showYn, String keepYn,
            MemberInfoRVO member) {
        this.storyNo = storyNo;
        this.id = id;
        this.storyFileName = storyFileName;
        this.storyTime = storyTime;
        this.showYn = showYn;
        this.keepYn = keepYn;
        this.member = member;
    }
}
