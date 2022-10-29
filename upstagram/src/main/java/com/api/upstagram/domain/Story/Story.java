package com.api.upstagram.domain.Story;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;
import com.api.upstagram.domain.memberInfo.MemberInfo;

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
public class Story extends BaseEntity{
    
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

}
