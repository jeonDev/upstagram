package com.api.upstagram.domain.Story;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;

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
public class StoryEntity extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyNo;
    
    private String id;
    private String storyFileName;
    private String storyTime;
    private String showYn;
    private String keepYn;

    @Builder
    public StoryEntity(Long storyNo, String id, String storyFileName, String storyTime, String showYn, String keepYn) {
        this.storyNo = storyNo;
        this.id = id;
        this.storyFileName = storyFileName;
        this.storyTime = storyTime;
        this.showYn = showYn;
        this.keepYn = keepYn;
    }
}
