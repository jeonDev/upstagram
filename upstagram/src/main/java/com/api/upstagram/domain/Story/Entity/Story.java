package com.api.upstagram.domain.Story.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.api.upstagram.vo.Story.StoryRVO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STORY")
@Entity
public class Story extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyNo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    private String storyFileName;
    private String storyTime;
    private String showYn;
    private String keepYn;
    
    @Builder
    public Story(Long storyNo, MemberInfo member, String storyFileName, String storyTime, String showYn,
            String keepYn) {
        this.storyNo = storyNo;
        this.member = member;
        this.storyFileName = storyFileName;
        this.storyTime = storyTime;
        this.showYn = showYn;
        this.keepYn = keepYn;
    }

    public StoryRVO storyToRVO() {
        return StoryRVO.builder()
                .storyNo(this.storyNo)
                .storyFileName(this.storyFileName)
                .storyTime(this.storyTime)
                .showYn(this.showYn)
                .keepYn(this.keepYn)
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .sex(this.member.getSex())
                .tel(this.member.getTel())
                .oauthNo(this.member.getOauthNo())
                .build();
    }
}
